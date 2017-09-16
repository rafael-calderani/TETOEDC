package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.api.UserAPI;
import br.com.calderani.rafael.tetoedc.api.Validation;
import br.com.calderani.rafael.tetoedc.dao.UserDAO;
import br.com.calderani.rafael.tetoedc.model.MockyUser;
import br.com.calderani.rafael.tetoedc.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNUP = 10001;
    private MockyUser mockyUser;
    private UserDAO userDAO;

    CallbackManager callbackManager;

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

        userDAO = new UserDAO(this);

        UserAPI userAPI = ApiUtils.getUserAPI();
        userAPI.getUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MockyUser>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),
                                String.format(getString(R.string.service_error_message), e.getMessage()),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(MockyUser mockyUser) {
                        LoginActivity.this.mockyUser = mockyUser;
                    }
                });

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
                                            }
                                        }
                                    }
                                    catch (JSONException e){
                                        Bundle b = new Bundle();
                                        b.putString("Type", e.toString());
                                        b.putString("Message", e.getMessage());
                                        FirebaseAnalytics.getInstance(LoginActivity.this)
                                                .logEvent("FacebookLoginError", b);
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender");
                    fbRequest.setParameters(parameters);
                    fbRequest.executeAsync();
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

        Boolean keepConnected = getPreferences(Context.MODE_PRIVATE)
                .getBoolean("KeepConnected", true);
        cbKeepConnected.setChecked(keepConnected);

        btSignIn.setEnabled(false);
    }

    @OnClick(R.id.btSignIn)
    public void signIn() {
        String userName = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!AuthenticateUser(userName, password)) return;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("KeepConnected", cbKeepConnected.isChecked());
        editor.putString("USER_EMAIL", userName);
        editor.apply();

        // Answer LoginEvent (Fabric io)
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

        User user = userDAO.authenticateUser(email, password);
        if (user == null) {
            if (mockyUser.getUsuario().equals(email) && mockyUser.getSenha().equals(password)) {
                // valid mocky user i need to redirect to new user screen to finish the registration
                Intent i = new Intent(LoginActivity.this, UserManagementActivity.class);
                i.putExtra("TYPE", "continue registration");
                i.putExtra("EMAIL", email);
                i.putExtra("PASSWORD", password);
                startActivityForResult(i , REQUEST_CODE_SIGNUP);
            }
            else {
                toastMessage = getString(R.string.user_invalid);
            }
            result = false;
        }
        else {
            CurrentUser.finishInstance();
            CurrentUser.initInstance(user);
        }

        Toast t = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
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
        if (userDAO.exists(userName)) {
            Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
            //TODO: send an e-mail with a new random 6 digit password
        }
        else {
            Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        }
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
    public void validateEmail() {
        String email = etEmail.getText().toString();
        if (mockyUser != null && mockyUser.getUsuario().equals(email))
            etEmail.setError(null); // ignore validations if it's the mocky user
        else
            Validation.isEmailAddress(etEmail, true,
                getResources().getString(R.string.validation_email));

    }

    @OnFocusChange({R.id.etPassword})
    public void validatePassword(){
        String password = etPassword.getText().toString();
        if (mockyUser != null && mockyUser.getSenha().equals(password))
            etPassword.setError(null); // ignore validations if it's the mocky user
        else
            Validation.validateMinimumLength(etPassword, 6,
                getResources().getString(R.string.validation_minlength));
    }

    @OnTextChanged({R.id.etEmail, R.id.etPassword})
    void checkErrors() {
        Button btSignIn = (Button) findViewById(R.id.btSignIn);

        btSignIn.setEnabled(
            etEmail.getError() == null && etPassword.getError() == null
        );
    }
}
