package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.api.CommunityAPI;
import br.com.calderani.rafael.tetoedc.api.Validation;
import br.com.calderani.rafael.tetoedc.dao.UserDAO;
import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class UserManagementActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.ddlCommunity)
    Spinner ddlCommunity;

    @BindView(R.id.ddlFunction)
    Spinner ddlFunction;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.btDeleteUser)
    Button btDeleteUser;

    @BindView(R.id.btSaveUser)
    Button btSaveUser;

    private ProgressDialog pd;
    private CommunityAPI mService;
    private static final int GPS_PERMISSION = 101;
    private ArrayList<String> communityNames = null;
    private ArrayAdapter<String> adapter = null;
    private String type = "";
    private String community = "";
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_user);
        ButterKnife.bind(this);

        userDAO = new UserDAO(this);

        pd =  new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIcon(R.mipmap.ic_launcher);
        pd.setTitle(R.string.app_name);
        pd.setMessage(getString(R.string.loading_communities));
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();

        checkErrors();

        type = this.getIntent().getStringExtra("TYPE");
        String email = this.getIntent().getStringExtra("EMAIL");
        String password = this.getIntent().getStringExtra("PASSWORD");
        String name = this.getIntent().getStringExtra("NAME");

        if (email != null) etEmail.setText(email);
        if (password != null) etPassword.setText(password);
        if (name != null) etName.setText(name);

        // Carrega ddlCommunity usando o serviço web via json
        communityNames = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, communityNames);
        mService = ApiUtils.getCommunitiesAPI();

        mService.getCommunities()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Community>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(), String.format(getString(R.string.service_error_message), e.getMessage()),
                            Toast.LENGTH_LONG).show();

                    LogUtil.e(e, String.format(getString(R.string.service_error_message), e.getMessage()));
                }

                @Override
                public void onNext(List<Community> communities) {
                    adapter.clear();
                    int selectedCommunity = 0;
                    for(Community c : communities) {
                        communityNames.add(c.getName());
                        if (c.getName() == community) {
                            selectedCommunity = communities.indexOf(c);
                        }
                    }
                    ddlCommunity.setAdapter(adapter);
                    ddlCommunity.setSelection(selectedCommunity);
                    pd.hide();

                    if (type != null && type.equals("update")) { // edit profile screen
                        User currentUser = CurrentUser.getInstance();

                        etEmail.setEnabled(false);
                        etEmail.setText(currentUser.getEmail());
                        etPassword.setText(currentUser.getPassword());
                        etName.setText(currentUser.getName());
                        String function = currentUser.getFunction();
                        community = currentUser.getCommunityName();
                        String[] functions = UserManagementActivity.this
                                .getResources().getStringArray(R.array.function_array);
                        int functionPos =
                                Arrays.asList(functions).indexOf(function);
                        ddlFunction.setSelection(functionPos);
                        etPhone.setText(currentUser.getPhone());

                        btDeleteUser.setVisibility(View.VISIBLE);
                    }
                    else if (type != null && type.equals("continue registration")) { // first login with facebook, google or twitter
                        Toast.makeText(UserManagementActivity.this,
                                R.string.user_creation_continue, Toast.LENGTH_LONG).show();

                        etEmail.setEnabled(false);
                        etPassword.setEnabled(false);
                    }
                }
            });
    }

    @OnClick(R.id.btSaveUser)
    public void saveUser() {
        final String login = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        final String name = etName.getText().toString();
        final String community = ddlCommunity.getSelectedItem().toString();
        final String function = ddlFunction.getSelectedItem().toString();
        final String phone = etPhone.getText().toString();

        boolean userExists = userDAO.exists(login);
        if (userExists && type == null) {
            Toast.makeText(this, R.string.existing_user, Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setEmail(login);
        user.setPassword(password); //TODO: encrypt password before adding to the db
        user.setName(name);
        user.setCommunityName(community);
        user.setFunction(function);
        user.setPhone(phone);

        boolean saveResult = userDAO.save(user);

        if (!saveResult) {
            Toast.makeText(this, R.string.user_creation_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        CurrentUser.finishInstance();
        CurrentUser.initInstance(user);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_EMAIL", login);
        editor.apply();


        Toast.makeText(this, R.string.user_creation_success,
                Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
                resultIntent.putExtra("USERNAME", login);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
    }

    @OnClick(R.id.btDeleteUser)
    public void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.user_deletion_confirm);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int message = R.string.user_deletion;
                if (!userDAO.delete(CurrentUser.getInstance().getEmail())) {
                    message = R.string.user_deletion_error;
                }

                Toast.makeText(UserManagementActivity.this, message, Toast.LENGTH_SHORT).show();
                CurrentUser.finishInstance();
                Intent i = new Intent(UserManagementActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnFocusChange({R.id.etEmail})
    public void validateEmail(boolean hasFocus) {
        Validation.isEmailAddress(etEmail, true,
                getResources().getString(R.string.validation_email));
        checkErrors();
    }

    @OnFocusChange({R.id.etPassword})
    public void validatePassword(boolean hasFocus){
        Validation.validateMinimumLength(etPassword, 6,
                getResources().getString(R.string.validation_minlength));
        checkErrors();
    }

    @OnFocusChange({R.id.etName})
    public void validateName(boolean hasFocus){
        Validation.hasText(etName, getResources().getString(R.string.validation_required_field));
        checkErrors();
    }

    @OnFocusChange({R.id.etPhone})
    public void validatePhone(boolean hasFocus){
        Validation.isPhoneNumber(etPhone, false,
                getResources().getString(R.string.validation_phone));
        checkErrors();
    }

    private void checkErrors() {
        btSaveUser.setEnabled(
                etEmail.getError() == null &&
                etPassword.getError() == null &&
                etName.getError() == null &&
                etPhone.getError() == null
        );
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("USERNAME", "");
        setResult(Activity.RESULT_CANCELED, resultIntent);
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case GPS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    // Sucesso!
                }
                break;
            }

        }
    }
}
