package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewUserActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.ddlFunction)
    Spinner ddlFunction;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btCreateUser)
    public void createUser() {
        String login = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();
        String function = ddlFunction.getSelectedItem().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        //TODO: Check if user exists and Add him to the database

        Intent resultIntent = new Intent();
        resultIntent.putExtra("USERNAME", login);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("USERNAME", "");
        setResult(Activity.RESULT_CANCELED, resultIntent);
        super.onBackPressed();
    }
}
