package br.com.calderani.rafael.tetoedc.api;

import java.util.List;

import br.com.calderani.rafael.tetoedc.model.Comunidade;
import br.com.calderani.rafael.tetoedc.model.Usuario;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Rafael on 09/07/2017.
 */

public interface ComunidadeAPI {

    @GET("/comunidades")
    Observable<List<Comunidade>> getComunidades();

    @GET("/comunidades/{comunidade}/equipeEDC")
    Observable<List<Usuario>> getEquipeFixa(@Path("comunidade") String comunidade);

}
