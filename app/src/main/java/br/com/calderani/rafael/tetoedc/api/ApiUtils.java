package br.com.calderani.rafael.tetoedc.api;

/**
 * Created by Rafael on 09/07/2017.
 */

public class ApiUtils {

    private static final String BASE_URL = "http://tetoedc.herokuapp.com";

    public static CommunityAPI getCommunitiesAPI() {
        return RetrofitClient.getClient(BASE_URL).create(CommunityAPI.class);
    }
}