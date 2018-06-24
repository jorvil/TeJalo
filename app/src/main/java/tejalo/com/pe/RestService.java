package tejalo.com.pe;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tejalo.com.pe.model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestService {

    @GET("api/usuarios")
    Call<List<Usuario>> listarUsuarios();

    @POST("api/registrarUsuario")
    Call<Usuario> grabarUsuario(@Body Usuario usuario);

    @GET("api/usuarios/{nombre}/{password}")
    Call<List<Usuario>> loguear(@Path("nombre") String nombre,@Path("password") String password);

}
