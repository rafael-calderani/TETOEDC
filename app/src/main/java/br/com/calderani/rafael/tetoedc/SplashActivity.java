package br.com.calderani.rafael.tetoedc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.facebook.AccessToken;
import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.calderani.rafael.tetoedc.dao.UserDAO;
import br.com.calderani.rafael.tetoedc.model.User;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 4000;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userDAO = new UserDAO(this);

        animateSplash();
    }

    private void animateSplash() {
        Animation splashAnimation = AnimationUtils.loadAnimation(this,
                R.anim.splash_animation);

        final Animation photo1Animation = AnimationUtils.loadAnimation(this,
                R.anim.splash_photos_animation);
        final Animation photo2Animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.splash_photos_animation);
        final Animation photo3Animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.splash_photos_animation);

        photo1Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                StartAnimation(R.id.ivPhoto2, photo2Animation);
            }
        });
        photo2Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                StartAnimation(R.id.ivPhoto3, photo3Animation);
            }
        });

        StartAnimation(R.id.ivSplash, splashAnimation);
        StartAnimation(R.id.ivPhoto1, photo1Animation);

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
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
            Bundle b = new Bundle();
            b.putString("Type", e.toString());
            b.putString("Message", e.getMessage());
            FirebaseAnalytics.getInstance(this).logEvent("SplashError", b);
        }
    }

    /**
     * Verifica se o usuário deseja se manter conectado e valida o usuario.
     *
     * @return Verdadeiro se o usuário existe e é válido, Falso caso contrário
     */
    private boolean AuthUser() {
        /** Check user on SQLite DB */
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp != null) {
            Boolean keepConnected = sp.getBoolean("KeepConnected", false);
            if (!keepConnected) return false;

            String email = sp.getString("USER_EMAIL", "");
            String userId;

            if (email.isEmpty()) return false;

            User currentUser;

            /** Verify Facebook Auth */
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null && !accessToken.isExpired()) {
                userId = accessToken.getUserId();
                currentUser = userDAO.authenticateUser(email, userId);
            }
            else { /** No social provider, validate user by email only */
                currentUser = userDAO.authenticateUser(email);
            }

            if (currentUser == null) return false;

            CurrentUser.initInstance(currentUser);

            // Answer LoginEvent (Fabric io)
            LoginEvent loginEvent = new LoginEvent();
            loginEvent.putCustomAttribute("User E-mail", email);
            loginEvent.putSuccess(true);
            Answers.getInstance().logLogin(loginEvent);

            return true;
        }

        return false;
    }

    private void StartAnimation(int ivId, Animation animation) {
        animation.reset();
        ImageView ivPhoto = (ImageView) findViewById(ivId);
        ivPhoto.setVisibility(View.VISIBLE);
        ivPhoto.clearAnimation();
        ivPhoto.startAnimation(animation);
    }
}
