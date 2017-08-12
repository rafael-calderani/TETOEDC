package br.com.calderani.rafael.tetoedc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.calderani.rafael.tetoedc.dao.UserDAO;
import br.com.calderani.rafael.tetoedc.model.User;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 4000;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Boolean isUserAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animateSplash();
    }

    private void animateSplash() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.splash_animation);
        anim.reset();

        ImageView iv = (ImageView) findViewById(R.id.ivSplash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = null;
                    if (AuthUser()) {
                        intent = new Intent(SplashActivity.this, NavigationActivity.class);
                    }
                    else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        catch (Throwable e) {
            String message = e.getMessage();
        }
    }

    /**
     * TODO: Autenticar usuário
     * caso exista e seja válido direcionar para NavigationActivity
     * se não direcionar par LoginActivity
     */
    private boolean AuthUser() {
        Boolean result = false;

        /** Verify User Auth */
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        result = (accessToken != null && !accessToken.isExpired()) || result;
        SharedPreferences sp = this.getSharedPreferences(
            getString(R.string.sharedpreferences_name),
            Context.MODE_PRIVATE);
        if (sp != null) {
            String email = sp.getString("USER_EMAIL", "");
            String userId = sp.getString("USER_ID", "");
            if (!email.isEmpty()) {
                UserDAO userDAO = new UserDAO(this);
                User currentUser = userDAO.authenticateUser(email, userId);
                CurrentUser.initInstance(currentUser);
            }
        }


        /** Verify Firebase Auth */
        /*
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    isUserAuthenticated = true;
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };*/

        //TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //String currentDeviceId = telephonyManager.getDeviceId();
        //String currentDeviceNumber = telephonyManager.getLine1Number();
        //result = (accessToken != null && !accessToken.isExpired()) || result;

        return result;
    }
}
