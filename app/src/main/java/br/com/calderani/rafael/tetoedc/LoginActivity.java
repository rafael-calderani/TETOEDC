package br.com.calderani.rafael.tetoedc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNUP = 10001;

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;

    @BindView(R.id.cbKeepConnected)
    CheckBox cbKeepConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharedPreferences sp = this.getSharedPreferences(getString(R.string.sharedpreferences_name), Context.MODE_PRIVATE);
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

        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
        LoginActivity.this.finish();
    }

    @OnClick(R.id.ivSignInFacebook)
    public void signInFacebook() {
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        // TODO: Facebook Auth
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
