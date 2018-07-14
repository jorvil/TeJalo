package tejalo.com.pe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.Adapter.ViajeListadoAdapter;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.RestService.RestService;

public class ReservarViajeActivity extends AppCompatActivity {

    private String url = "http://192.168.137.2:8888/";
    //private String url="http://intranet.fridaysperu.com:8888/";

    private Spinner spiOrigen;
    private ArrayAdapter<Distrito> adapterDistritoOrigen;
    private Long distritoOrigen;

    private Spinner spiDestino;
    private ArrayAdapter<Distrito> adapterDistritoDestino;
    private Long distritoDestino;

    private Retrofit retrofit;
    private RestService restService;

    private ListView listViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_viaje);

        spiOrigen = findViewById(R.id.spiOrigen);
        spiDestino = findViewById(R.id.spiDestino);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //

        listarDistrito();
    }

    public void listarViaje(Long distritoOrigen, Long distritoDestino, String fecha) {
        restService.buscarViaje(distritoOrigen, distritoDestino, fecha).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                int resultado;

                List<Viaje> listViaje = response.body();

                if (listViaje == null) {

                    Toast.makeText(getApplicationContext(), "*** Viajes no encontrados ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = listViaje.size();

                    if (resultado > 0) {
                        ViajeListadoAdapter adapter = new ViajeListadoAdapter(getApplicationContext(), listViaje);
                        listViajes.setAdapter(adapter);
                    } else {
                        listViajes.setAdapter(null);
                        Toast.makeText(getApplicationContext(), "Viajes no encontrados", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });

    }

    private void listarDistrito() {
        restService.listarDistrito().enqueue(new Callback<List<Distrito>>() {
            @Override
            public void onResponse(Call<List<Distrito>> call, Response<List<Distrito>> response) {
                List<Distrito> distritoList = response.body();
                //Origen
                adapterDistritoOrigen = new ArrayAdapter<Distrito>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, distritoList);
                adapterDistritoOrigen.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiOrigen.setAdapter(adapterDistritoOrigen);
                //Origen
                adapterDistritoDestino = new ArrayAdapter<Distrito>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, distritoList);
                adapterDistritoDestino.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiDestino.setAdapter(adapterDistritoDestino);
                //Destino
                Log.d("RestService", "onResponse: " + distritoList.size());

            }

            @Override
            public void onFailure(Call<List<Distrito>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });
    }

}
