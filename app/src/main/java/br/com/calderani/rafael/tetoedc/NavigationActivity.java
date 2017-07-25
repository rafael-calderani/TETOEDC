package br.com.calderani.rafael.tetoedc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tvWelcome)
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //TODO: Check how to work with this to make actions Snackbar.make()
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddProject);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "This will take you to the New Project Activity.", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sp = this.getSharedPreferences(getString(R.string.sharedpreferences_name), Context.MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String date = df.format(c.getTime());

        String welcomeMsg = String.format(getString(R.string.welcome_text), sp.getString("UserName",""), "Olaria", date);
        tvWelcome.setText(welcomeMsg);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        // TODO: Navigation
        switch (id) {
            case R.id.nav_profile:
                break;
            case R.id.nav_schedule:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_contact:
                break;
            case R.id.nav_faq:
                break;
            case R.id.nav_help:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.btSchedule)
    public void scheduleClick() {
        //String url = "https://docs.google.com/spreadsheets/d/1yFKZW6JB4ovbC3yIWmbvXv8OMq0Q5avjSznn-xkq0SM/edit#gid=0";
        //navigateToUrl(url);
    }

    @OnClick(R.id.btTeam)
    public void teamClick() {
        //String url = "https://docs.google.com/spreadsheets/d/14C30CS9DRHU-K2PpjK1P1mN27k39gYO547etJtKPDrI/edit#gid=0";
        //navigateToUrl(url);
    }

    @OnClick(R.id.btProjects)
    public void projectsClick() {
        //String url = "https://docs.google.com/spreadsheets/d/1cxO3vSNVOE8W7xWfZiHSmSDMIid4_Dp1hll1Lnk61Vg/edit#gid=1511351235";
        //navigateToUrl(url);
    }

    @OnClick(R.id.btNews)
    public void newsClick() {
        //String url = "";
        //navigateToUrl(url);
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btCommunities)
    public void communitiesClick() {
        Intent i = new Intent(this, CommunitiesActivity.class);
        startActivity(i);
    }

    private void navigateToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
