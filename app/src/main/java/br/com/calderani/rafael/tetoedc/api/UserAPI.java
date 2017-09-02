package br.com.calderani.rafael.tetoedc.api;

import br.com.calderani.rafael.tetoedc.model.MockyUser;
import br.com.calderani.rafael.tetoedc.model.User;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Rafael on 30/08/2017.
 */

public interface UserAPI {
    @GET("/v2/58b9b1740f0000b614f09d2f")
    Observable<MockyUser> getUser();
}
