package br.com.calderani.rafael.tetoedc;

import android.*;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.calderani.rafael.tetoedc.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.DIAL_CALL_PERMISSION;
import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.GPS_FINE_PERMISSION;
import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.SEND_MESSAGE_PERMISSION;

public class AboutActivity extends AppCompatActivity {
    String phoneNumber;

    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            if (info != null) {
                tvAppVersion.setText(info.versionName);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        User currentUser = CurrentUser.getInstance();
        if (currentUser != null && !currentUser.getPhone().isEmpty()) {
            phoneNumber = currentUser.getPhone();
        }
        else { phoneNumber = "11963638415"; }
    }

    @AfterPermissionGranted(DIAL_CALL_PERMISSION)
    @OnClick({R.id.ivCallMe, R.id.tvCallMe})
    public void dialContactPhone() {
        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.CALL_PHONE)) {
            startActivity(
                    new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
        }
        else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.phoneCallPermissionRequest),
                    DIAL_CALL_PERMISSION,
                    android.Manifest.permission.CALL_PHONE);
        }
    }

    @AfterPermissionGranted(SEND_MESSAGE_PERMISSION)
    @OnClick({R.id.ivMessageMe, R.id.tvMessageMe})
    public void sendSMSMessage() {
        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.SEND_SMS)) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
            //sendIntent.putExtra("sms_body", "Message contents.");
            startActivity(sendIntent);
            try {
                startActivity(sendIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.resource_unavailable, Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putString("Type", e.toString());
                b.putString("Message", e.getMessage());
                FirebaseAnalytics.getInstance(this).logEvent("SMS_Message_Error", b);
            }
        }
        else {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.smsPermissionRequest),
                    SEND_MESSAGE_PERMISSION,
                    Manifest.permission.SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, results, this);
    }
}
