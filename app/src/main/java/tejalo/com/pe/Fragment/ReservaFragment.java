package tejalo.com.pe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.Adapter.ReservaListadoAdapter;
import tejalo.com.pe.Globales;
import tejalo.com.pe.Model.Reserva;
import tejalo.com.pe.R;
import tejalo.com.pe.ReservarViajeActivity;
import tejalo.com.pe.RestService.RestService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private String url = Globales.url;

    private static final int REQUEST_RESERVA= 1;

    private Retrofit retrofit;
    private RestService restService;

    private ListView listReserva;
    private Button btnReservar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);

        listReserva = view.findViewById(R.id.listReserva);
        btnReservar = view.findViewById(R.id.btnReservar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //
        btnReservar.setOnClickListener(this);

        listarReservaxPasajero(Globales.usuario);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RESERVA) {
            switch (resultCode) {
                case -1:
                    listarReservaxPasajero(Globales.usuario);
                    break;
            }
        }

    }

    public void listarReservaxPasajero(Long idPasajero) {
        restService.listarReservaxPasajero(idPasajero).enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                int resultado;

                List<Reserva> reservaList = response.body();

                if (reservaList == null) {

                    Toast.makeText(getActivity(), "*** Reservas no encontradas ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = reservaList.size();

                    if (resultado > 0) {
                        ReservaListadoAdapter adapter = new ReservaListadoAdapter(getActivity(), reservaList);
                        listReserva.setAdapter(adapter);
                    } else {
                        listReserva.setAdapter(null);
                        Toast.makeText(getActivity(), "Reservas no encontradas", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });

    }

    public void abrirReserva() {
        Intent intent = new Intent(getActivity(), ReservarViajeActivity.class);
        startActivityForResult(intent,REQUEST_RESERVA);
    }

}
