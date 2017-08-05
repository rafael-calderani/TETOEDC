package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNUP = 10001;
    CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;

    @BindView(R.id.cbKeepConnected)
    CheckBox cbKeepConnected;

    @BindView(R.id.ivSignInFacebook)
    ImageView ivSignInFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        /*
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        */

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance()
            .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    /*
                    GraphRequest fbRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try{
                                        if (object != null && !object.isNull("email")) {
                                            //TODO: Insert or verify user on database?
                                            //etUserName.setText(object.getString("email"));
                                            //etPassword.setText("FBUser**");
                                            signIn();
                                        }
                                    }
                                    catch (FacebookException e){
                                        String a = e.getMessage();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender");
                    fbRequest.setParameters(parameters);
                    fbRequest.executeAsync();
                    */
                    handleFacebookAccessToken(loginResult.getAccessToken());
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
        etUserName.setText(sp.getString("UserName",""));
        cbKeepConnected.setChecked(sp.getBoolean("KeepConnected", true));
    }

    @OnClick(R.id.btSignIn)
    public void signIn() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (!AuthenticateUser(userName, password)) return;

        SharedPreferences sp = this.getSharedPreferences("TETOEDCInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        spEditor.putString("UserName", userName);
        spEditor.putBoolean("KeepConnected", cbKeepConnected.isChecked());
        spEditor.commit();

        //Crashlytics.setUserIdentifier(etUserName.getText().toString());
        //Crashlytics.setUserEmail(etUserName.getText().toString());
        //Answers.getInstance().logLogin(new LoginEvent());

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

    private boolean AuthenticateUser(String userName, String password) {
        boolean result = true;
        if (userName.isEmpty()) {
            Toast.makeText(this,
                    String.format(getString(R.string.blank_field), getString(R.string.hint_username)),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, String.format(getString(R.string.blank_field), getString(R.string.hint_password)),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }

        //TODO: Authenticate user on the database
        // Store user information on a singleton? or SharedPreferences
        //firebaseAuth = FirebaseAuth.getInstance();
        //https://api-project-834710560139.firebaseapp.com/__/auth/handler

        return result;
    }

    @OnClick(R.id.btSignUp)
    public void signUp() {
        Intent i = new Intent(this, NewUserActivity.class);
        startActivityForResult(i, REQUEST_CODE_SIGNUP);
    }

    @OnClick(R.id.tvResetPassword)
    public void resetPassword() {
        String userName = etUserName.getText().toString();
        if (userName == "") {
            Toast.makeText(this,
                    String.format(
                            getString(R.string.blank_field),
                            getString(R.string.hint_username)),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        //TODO: check if user exists and send an e-mail with a new random 6 digit password
        //TODO: request user permission to send e-mail?
    }

    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> loginTask) {
                        if (loginTask.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGNUP) {
            switch (resultCode) {
                case RESULT_OK:
                    etUserName.setText(data.getStringExtra("USERNAME"));
                    etPassword.requestFocus();
                    break;

                case RESULT_CANCELED:
                    etUserName.setText("");
                    break;
            }
        }
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
