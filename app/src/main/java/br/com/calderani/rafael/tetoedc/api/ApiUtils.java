package br.com.calderani.rafael.tetoedc.api;

/**
 * Created by Rafael on 09/07/2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://tetoedc.herokuapp.com";

    public static CommunityAPI getComunidadeAPI() {
        return RetrofitClient.getClient(BASE_URL).create(CommunityAPI.class);
    }
}