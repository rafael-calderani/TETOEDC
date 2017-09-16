package br.com.calderani.rafael.tetoedc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.graphics.BitmapFactory.decodeResource;
import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.EXTERNAL_STORAGE_PERMISSION;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private ShareActionProvider mShareActionProvider;

    @BindView(R.id.tvWelcome)
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseMessaging.getInstance().subscribeToTopic("noticiasTETO");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: get next event date on the calendar
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String date = df.format(c.getTime());

        String welcomeMsg = String.format(
                getString(R.string.welcome_text),
                CurrentUser.getInstance().getName(),
                CurrentUser.getInstance().getCommunityName(), date);
        tvWelcome.setText(welcomeMsg);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            logout();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_quit:
                quit();
                return true;
            case R.id.action_share:
                shareContent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //TODO: FAQ navigation option and market research for possible FAQs (google forms?)

        switch (id) {
            case R.id.nav_profile:
                Intent i = new Intent(this, UserManagementActivity.class);
                i.putExtra("TYPE", "update");
                startActivity(i);
                break;
            case R.id.nav_schedule:
                Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_contact:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_help:
                Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.btSchedule)
    public void scheduleClick() {
        Intent i = new Intent(this, ScheduleActivity.class);
        startActivity(i);

        Bundle b = new Bundle();
        b.putString("Click", "Schedule");
        mFirebaseAnalytics.logEvent("Schedule_Activity_Start", b);
    }

    @OnClick(R.id.btTeam)
    public void teamClick() {
        String url = "https://docs.google.com/spreadsheets/d/14C30CS9DRHU-K2PpjK1P1mN27k39gYO547etJtKPDrI/edit#gid=0";
        navigateToUrl(url);

        Bundle b = new Bundle();
        b.putString("Click", "Team");
        mFirebaseAnalytics.logEvent("Team_Link_Activated", b);
    }

    @OnClick(R.id.btProjects)
    public void projectsClick() {
        //String url = "https://docs.google.com/spreadsheets/d/1cxO3vSNVOE8W7xWfZiHSmSDMIid4_Dp1hll1Lnk61Vg/edit#gid=1511351235";
        //navigateToUrl(url);

        startActivity(new Intent(this, ProjectsActivity.class));

        Bundle b = new Bundle();
        b.putString("Click", "Projects");
        mFirebaseAnalytics.logEvent("Projects_Link_Activated", b);
    }

    @OnClick(R.id.btNews)
    public void newsClick() {
        //String url = "";
        //navigateToUrl(url);
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();

        Bundle b = new Bundle();
        b.putString("Click", "News");
        mFirebaseAnalytics.logEvent("News_Link_Activated", b);
    }

    @OnClick(R.id.btCommunities)
    public void communitiesClick() {
        startActivity(new Intent(this, CommunitiesActivity.class));

        Bundle b = new Bundle();
        b.putString("Click", "Communities");
        mFirebaseAnalytics.logEvent("Communities_Activity_Start", b);
    }

    private void navigateToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void logout(){
        (new ApiUtils()).ConfirmationDialog(this, R.string.logout_confirm,
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: logout on: Google and Twitter
                LoginManager fbManager = LoginManager.getInstance();
                if (fbManager != null) fbManager.logOut();

                CurrentUser.finishInstance();
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(NavigationActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("USER_EMAIL", "");
                editor.apply();

                Intent i = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(i);
                dialog.dismiss();
            }
        }, null);
    }

    private void quit() {
        (new ApiUtils()).ConfirmationDialog(this, R.string.quit_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: logout on: Google and Twitter
                LoginManager fbManager = LoginManager.getInstance();
                if (fbManager != null) fbManager.logOut();

                CurrentUser.finishInstance();

                finishAffinity(); // finish this and all underlying activities if there are any
            }
        }, null);
    }

    @AfterPermissionGranted(EXTERNAL_STORAGE_PERMISSION)
    private void shareContent() {
        if(!EasyPermissions.hasPermissions(NavigationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(NavigationActivity.this,
                    getString(R.string.storagePermissionRequest), EXTERNAL_STORAGE_PERMISSION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        else {
            Bitmap b = decodeResource(getResources(), R.drawable.splash);
            String url = MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    b, "Splash", "Awesome App - Splash");

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message)); //TODO: add link to the app on the play store
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share with:"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, results, this);
    }
}
