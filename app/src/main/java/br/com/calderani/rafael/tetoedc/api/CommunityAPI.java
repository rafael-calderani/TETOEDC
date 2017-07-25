package br.com.calderani.rafael.tetoedc.api;

import java.util.List;

import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Rafael on 09/07/2017.
 */

public interface CommunityAPI {

    @GET("/comunidades")
    Observable<List<Community>> getCommunities();

    @GET("/comunidades/{comunidade}/details")
    Observable<List<Community>> getCommunityDetails(@Path("comunidade") String comunidade);

}
