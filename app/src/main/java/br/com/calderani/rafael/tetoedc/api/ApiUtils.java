package br.com.calderani.rafael.tetoedc.api;

/**
 * Created by Rafael on 09/07/2017.
 */

public class ApiUtils {

    private static final String HEROKU_URL = "http://tetoedc.herokuapp.com";
    private static final String MOCKY_URL = "http://www.mocky.io";

    public static CommunityAPI getCommunitiesAPI() {
        return RetrofitClient.getClient(HEROKU_URL).create(CommunityAPI.class);
    }

    public static UserAPI getUserAPI() {
        return RetrofitClient.getClient(MOCKY_URL).create(UserAPI.class);
    }
}