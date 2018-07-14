package tejalo.com.pe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.Adapter.ViajeListadoAdapter;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.R;
import tejalo.com.pe.ReservarViajeActivity;
import tejalo.com.pe.RestService.RestService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private String url = "http://192.168.137.2:8888/";
    //private String url="http://intranet.fridaysperu.com:8888/";

    private Retrofit retrofit;
    private RestService restService;

    private Button btnReservar;

    private ListView listReservas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);

        btnReservar = view.findViewById(R.id.btnReservar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //
        btnReservar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReservar:
                abrirReserva();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void listarRerva(Long distritoOrigen, Long distritoDestino, String fecha) {
        restService.buscarViaje(distritoOrigen, distritoDestino, fecha).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                int resultado;

                List<Viaje> listViaje = response.body();

                if (listViaje == null) {

                    Toast.makeText(getActivity(), "*** Reservas no encontradas ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = listViaje.size();

                    if (resultado > 0) {
                        ViajeListadoAdapter adapter = new ViajeListadoAdapter(getActivity(), listViaje);
                        listReservas.setAdapter(adapter);
                    } else {
                        listReservas.setAdapter(null);
                        Toast.makeText(getActivity(), "Reservas no encontradas", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });

    }

    public void abrirReserva() {
        Intent intent = new Intent(getActivity(), ReservarViajeActivity.class);
        startActivity(intent);
    }


}
