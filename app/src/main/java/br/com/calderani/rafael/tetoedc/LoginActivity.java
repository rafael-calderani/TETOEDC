package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import br.com.calderani.rafael.tetoedc.api.Validation;
import br.com.calderani.rafael.tetoedc.dao.UserDAO;
import br.com.calderani.rafael.tetoedc.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNUP = 10001;
    CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;

    @BindView(R.id.cbKeepConnected)
    CheckBox cbKeepConnected;

    @BindView(R.id.ivSignInFacebook)
    ImageView ivSignInFacebook;

    @BindView(R.id.btSignIn)
    Button btSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance()
            .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest fbRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try{
                                        if (object != null && !object.isNull("email")) {
                                            String email = object.getString("email");
                                            String name = object.getString("name");
                                            String userId = object.getString("id");

                                            UserDAO userDAO = new UserDAO(LoginActivity.this);
                                            if (userDAO.exists(email)) {
                                                etEmail.setText(email);
                                                etPassword.setText(userId);
                                                signIn();
                                            }
                                            else {
                                                // New user with valid FB Auth
                                                // I have to redirect him to the UserManagementActivity
                                                Intent i = new Intent(LoginActivity.this,
                                                        UserManagementActivity.class);
                                                i.putExtra("TYPE", "continue registration");
                                                i.putExtra("EMAIL", email);
                                                i.putExtra("PASSWORD", userId);
                                                i.putExtra("NAME", name);
                                                startActivityForResult(i , REQUEST_CODE_SIGNUP);

                                                // Update SharedPreferences
                                                SharedPreferences sp =
                                                    LoginActivity.this.getSharedPreferences(
                                                        getString(R.string.sharedpreferences_name),
                                                        Context.MODE_PRIVATE);
                                                SharedPreferences.Editor spEditor = sp.edit();
                                                spEditor.putString("USER_EMAIL", email);
                                                spEditor.putString("USER_ID", userId);
                                                spEditor.commit();
                                            }
                                        }
                                    }
                                    catch (JSONException e){
                                        String a = e.getMessage();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender");
                    fbRequest.setParameters(parameters);
                    fbRequest.executeAsync();

                    //handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() { /* App code */ }

                @Override
                public void onError(FacebookException exc) {
                    Toast.makeText(null,
                            String.format(getString(R.string.service_error_message), exc.getMessage()),
                            Toast.LENGTH_SHORT).show();
                }
            });

        ivSignInFacebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Activity a = (Activity) v.getContext();
                LoginManager.getInstance().logInWithReadPermissions(a, Arrays.asList("email"));
            }
        });

        SharedPreferences sp = this.getSharedPreferences(
                getString(R.string.sharedpreferences_name),
                Context.MODE_PRIVATE);
        etEmail.setText(sp.getString("UserName",""));
        cbKeepConnected.setChecked(sp.getBoolean("KeepConnected", true));

        btSignIn.setEnabled(false);
    }

    @OnClick(R.id.btSignIn)
    public void signIn() {
        String userName = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!AuthenticateUser(userName, password)) return;

        SharedPreferences sp = this.getSharedPreferences("TETOEDCInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        spEditor.putString("UserName", userName);
        spEditor.putBoolean("KeepConnected", cbKeepConnected.isChecked());
        spEditor.commit();

        // TODO: create Answer LoginEvent (Fabric io)
        //Crashlytics.setUserEmail(etEmail.getText().toString());
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.putCustomAttribute("User E-mail", userName);
        loginEvent.putSuccess(true);
        Answers.getInstance().logLogin(loginEvent);

        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    @OnClick(R.id.ivSignInGoogle)
    public void signInGoogle() {
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        // TODO: Google Auth
    }

    @OnClick(R.id.ivSignInTwitter)
    public void signInTwitter() {
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        // TODO: Twitter Auth
    }

    private boolean AuthenticateUser(String email, String password) {
        boolean result = true;
        String toastMessage = getString(R.string.user_auth_success);

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.authenticateUser(email, password);
        if (user == null) {
            toastMessage = getString(R.string.user_invalid);
            result = false;
        }
        else {
            CurrentUser.finishInstance();
            CurrentUser.initInstance(user);
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        return result;
    }

    @OnClick(R.id.btSignUp)
    public void signUp() {
        Intent i = new Intent(this, UserManagementActivity.class);
        startActivityForResult(i, REQUEST_CODE_SIGNUP);
    }

    @OnClick(R.id.tvResetPassword)
    public void resetPassword() {
        String userName = etEmail.getText().toString();
        if (userName == "") {
            Toast.makeText(this,
                    String.format(
                            getString(R.string.blank_field),
                            getString(R.string.email)),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        //TODO: check if user exists and send an e-mail with a new random 6 digit password
        //TODO: request user permission to send e-mail?
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGNUP) {
            switch (resultCode) {
                case RESULT_OK:
                    etEmail.setText(data.getStringExtra("USERNAME"));
                    etPassword.setText(CurrentUser.getInstance().getPassword());
                    signIn();
                    break;

                case RESULT_CANCELED:
                    etEmail.setText("");
                    break;
            }
        }
    }

    @OnFocusChange({R.id.etEmail})
    public void validateEmail(boolean hasFocus) {
        if (!hasFocus) Validation.isEmailAddress(etEmail, true);
        checkErrors();

    }

    @OnFocusChange({R.id.etPassword})
    public void validatePassword(boolean hasFocus){
        if (!hasFocus) Validation.validateMinimumLength(etPassword, 6, true);
        checkErrors();
    }

    private void checkErrors() {
        Button btSignIn = (Button) findViewById(R.id.btSignIn);
        btSignIn.setEnabled(
            etEmail.getError() == null && etPassword.getError() == null
        );
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
    */
}
