package tejalo.com.pe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.Adapter.ReservaListadoAdapter;
import tejalo.com.pe.Adapter.ReservaListadoConductorAdapter;
import tejalo.com.pe.Model.Reserva;
import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.RestService.RestService;

public class ConfirmarReservaActivity extends AppCompatActivity implements View.OnClickListener{

    private String url = Globales.url;

    private Retrofit retrofit;
    private RestService restService;

    private TextView txtFecha;
    private TextView txtOrigen;
    private TextView txtDestino;
    private ListView listReserva;

    private Button btnConfirmar;
    private Button btnCancelar;

    List<Reserva> reservaList;

    private Long viaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_reserva);

        txtFecha = findViewById(R.id.txtFecha);
        txtOrigen = findViewById(R.id.txtOrigen);
        txtDestino = findViewById(R.id.txtDestino);
        listReserva = findViewById(R.id.listReserva);

        btnConfirmar = findViewById(R.id.btnConfimar);
        btnCancelar = findViewById(R.id.btnCancelar);

        viaje = getIntent().getLongExtra("viaje", 0);
        String fecha = getIntent().getStringExtra("fecha");
        String origen = getIntent().getStringExtra("origen");
        String destino = getIntent().getStringExtra("destino");

        txtFecha.setText(fecha);
        txtOrigen.setText(origen);
        txtDestino.setText(destino);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //

        btnConfirmar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        listarReservaConductor(viaje);

    }

    public void listarReservaConductor(Long idViaje) {
        restService.listarReservaConductor(idViaje).enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                int resultado;

                reservaList = response.body();

                if (reservaList == null) {

                    Toast.makeText(getApplicationContext(), "*** Reservas no encontradas ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = reservaList.size();

                    if (resultado > 0) {
                        ReservaListadoConductorAdapter adapter = new ReservaListadoConductorAdapter(getBaseContext(), reservaList);
                        listReserva.setAdapter(adapter);
                    } else {
                        listReserva.setAdapter(null);
                        Toast.makeText(getApplicationContext(), "Reservas no encontradas", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfimar:
                terminarViaje(viaje);
                break;

        }
    }

    private void terminarViaje(Long idViaje) {

        restService.terminarViaje(idViaje).enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {

                if (response.isSuccessful()) {
                    //String mensaje = response.body().toString();
                    Log.e("post submitted to API.", response.body().toString());
                    Toast.makeText(ConfirmarReservaActivity.this, "Reservas confirmadas", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });
    }

}
