package br.com.calderani.rafael.tetoedc;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Rafael on 18/07/2017.
 */

public class StethoApplication extends Application {
    public void onCreate(){
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
