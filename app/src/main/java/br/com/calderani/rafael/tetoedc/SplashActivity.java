package br.com.calderani.rafael.tetoedc;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.AccessToken;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 4000;

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

        /** Verify Facebook Auth */
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        result = (accessToken != null && !accessToken.isExpired()) || result;

        /** Verify Firebase Auth */
        //TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //String currentDeviceId = telephonyManager.getDeviceId();
        //String currentDeviceNumber = telephonyManager.getLine1Number();
        //result = (accessToken != null && !accessToken.isExpired()) || result;

        return result;
    }
}
