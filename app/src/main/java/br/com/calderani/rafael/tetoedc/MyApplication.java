package br.com.calderani.rafael.tetoedc;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Rafael on 18/07/2017.
 */

public class MyApplication extends Application {
    public void onCreate(){
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        Fabric.with(this, new Crashlytics());
    }
}
