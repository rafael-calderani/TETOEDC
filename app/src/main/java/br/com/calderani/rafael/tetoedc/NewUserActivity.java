package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.api.CommunityAPI;
import br.com.calderani.rafael.tetoedc.model.Community;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class NewUserActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.ddlCommunity)
    Spinner ddlCommunity;

    @BindView(R.id.ddlFunction)
    Spinner ddlFunction;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPhone)
    EditText etPhone;

    private CommunityAPI mService;
    private static final int GPS_PERMISSION = 101;
    private ArrayList<String> communityNames = null;
    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);
        // Carregar ddlCommunity usando o serviço web via json
        communityNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, communityNames);
        mService = ApiUtils.getComunidadeAPI();
        //TODO: Show LoadingActivity
        mService.getCommunities()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Community>>() {
                @Override
                public void onCompleted() {}

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(), String.format(getString(R.string.service_error_message), e.getMessage()),
                            Toast.LENGTH_LONG).show();
                    //TODO: Verificar Log do Stetho
                    // LogUtil.e(e, String.format(getString(R.string.service_error_message), e.getMessage()));
                }

                @Override
                public void onNext(List<Community> communities) {
                    adapter.clear();
                    for(Community c : communities) {
                        communityNames.add(c.getName());
                    }
                    ddlCommunity.setAdapter(adapter);
                }
            });
    }

    @OnClick(R.id.btCreateUser)
    public void createUser() {
        String login = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String community = ddlCommunity.getSelectedItem().toString();
        String function = ddlFunction.getSelectedItem().toString();
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
