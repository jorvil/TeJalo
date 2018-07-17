package tejalo.com.pe.RestService;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Reniec;
import tejalo.com.pe.Model.Reserva;
import tejalo.com.pe.Model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import tejalo.com.pe.Model.Viaje;

public interface RestService {

    //Listado de los Distritos
    @GET("api/distritos")
    Call<List<Distrito>> listarDistrito();

    //Listado de los Usuarios
    @GET("api/usuarios")
    Call<List<Usuario>> listarUsuarios();

    //Grabar Usuario
    @POST("api/registrarUsuario")
    Call<Usuario> grabarUsuario(@Body Usuario usuario);

    //Validar usuario y password de Usuario
    @GET("api/usuario/{nombre}/{password}")
    Call<List<Usuario>> loguear(@Path("nombre") String nombre,@Path("password") String password);

    //Grabar Viaje
    @POST("api/registrarViaje")
    Call<Viaje> grabarViaje(@Body Viaje viaje);

    //Listado de Viajes por Pasajero
    @GET("api/viaje/{distritoorigen}/{distritodestino}/{fecha}/{codigo}")
    Call<List<Viaje>> listarViajexPasajero(@Path("distritoorigen") Long distritoOrigen,@Path("distritodestino") Long distritoDestino,@Path("fecha") String fecha,@Path("codigo") Long codigo);

    //Grabar Reserva
    @POST("api/reservarViaje")
    Call<Reserva> grabarReserva(@Body Reserva reserva);

    //Listado de Reservas del pasajero
    @GET("api/reservaPasajero/{idPasajero}")
    Call<List<Reserva>> listarReservaxPasajero(@Path("idPasajero") Long idPasajero);

    //Listado de Viajes por Conductor
    @GET("api/viajeConductor/{idConductor}")
    Call<List<Viaje>> listarViajexConductor(@Path("idConductor") Long codigo);

    //Buscar DNI en la Reniec
    @GET("api/Reniec/{DNI}")
    Call<Reniec> buscarDni(@Path("DNI") Long dni);

}
