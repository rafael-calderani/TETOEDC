package br.com.calderani.rafael.tetoedc.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafael on 09/07/2017.
 */

public class RetrofitClient {

    private static Retrofit mockyRetrofit = null;
    private static Retrofit herokuRetrofit = null;


    public static Retrofit getClient(String client, String baseUrl) {
        Retrofit retrofit = (client == "heroku") ? herokuRetrofit : mockyRetrofit;
        if (retrofit==null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
