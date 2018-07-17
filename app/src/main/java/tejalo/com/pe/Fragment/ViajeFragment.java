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
import tejalo.com.pe.Adapter.ViajeListadoConductorAdapter;
import tejalo.com.pe.Globales;
import tejalo.com.pe.Model.Usuario;
import tejalo.com.pe.PublicarViajeActivity;
import tejalo.com.pe.R;
import tejalo.com.pe.RestService.RestService;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Viaje;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViajeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String url = Globales.url;

    private static final int REQUEST_VIAJE= 1;

    private Retrofit retrofit;
    private RestService restService;

    private ListView listViaje;
    private Button btnPublicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viaje, container, false);

        listViaje = view.findViewById(R.id.listViaje);
        btnPublicar = view.findViewById(R.id.btnPublicar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //
        btnPublicar.setOnClickListener(this);

        listarViajexConductor(Globales.usuario);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPublicar:
                abrirViaje();
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

        if (requestCode == REQUEST_VIAJE) {
            switch (resultCode) {
                case -1:
                    listarViajexConductor(Globales.usuario);
                    break;
            }
        }

    }

    public void abrirViaje() {
        Intent intent = new Intent(getActivity(), PublicarViajeActivity.class);
        startActivityForResult(intent,REQUEST_VIAJE);
    }

    public void listarViajexConductor(Long idConductor) {
        restService.listarViajexConductor(idConductor).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                int resultado;

                List<Viaje> viajeList = response.body();

                if (viajeList == null) {

                    Toast.makeText(getActivity(), "*** Viajes no encontrados ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = viajeList.size();

                    if (resultado > 0) {
                        ViajeListadoConductorAdapter adapter = new ViajeListadoConductorAdapter(getActivity(), viajeList);
                        listViaje.setAdapter(adapter);
                    } else {
                        listViaje.setAdapter(null);
                        Toast.makeText(getActivity(), "Viajes no encontrados", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });

    }

}
