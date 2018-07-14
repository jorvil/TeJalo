package tejalo.com.pe.RestService;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import tejalo.com.pe.Model.Viaje;

public interface RestService {

    @GET("api/usuarios")
    Call<List<Usuario>> listarUsuarios();

    @POST("api/registrarUsuario")
    Call<Usuario> grabarUsuario(@Body Usuario usuario);

    @GET("api/usuario/{nombre}/{password}")
    Call<List<Usuario>> loguear(@Path("nombre") String nombre,@Path("password") String password);

    @GET("api/distritos")
    Call<List<Distrito>> listarDistrito();

    @POST("api/registrarViaje")
    Call<Viaje> grabarViaje(@Body Viaje viaje);

    @GET("api/buscarViaje/{distritoOrigen}/{distritoDestino}/{fecha}")
    Call<List<Viaje>> buscarViaje(@Path("distritoOrigen") Long distritoOrigen,@Path("distritoDestino") Long distritoDestino,@Path("fecha") String fecha);

}
