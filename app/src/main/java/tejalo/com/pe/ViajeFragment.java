package tejalo.com.pe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.model.Distritos;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViajeFragment extends Fragment {

    private Retrofit retrofit;
    private RestService restService;

    private Spinner spiPartida;
    private ArrayAdapter<Distritos> adapterDistritos;

    //private String url = "http://192.168.43.116:8888/";
    private String url="http://intranet.fridaysperu.com:8888/";

    public ViajeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viaje, container, false);

        spiPartida =  (Spinner) view.findViewById(R.id.spiPartida);

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RestService.class);

        listarDistritos();

        return view;
    }

    private void listarDistritos() {
        restService.listarDistritos().enqueue(new Callback<List<Distritos>>() {
            @Override
            public void onResponse(Call<List<Distritos>> call, Response<List<Distritos>> response) {
                List<Distritos> distritosList = response.body();

                //
                adapterDistritos = new ArrayAdapter<Distritos>(getActivity(),
                        android.R.layout.simple_spinner_item, distritosList);
                adapterDistritos.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiPartida.setAdapter(adapterDistritos);
                //

                Log.d("RestService", "onResponse: " + distritosList.size());
            }

            @Override
            public void onFailure(Call<List<Distritos>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });
    }

    private void mostrarFecha() {
        String dateFormat = "dd/MM/yyyy";
        //SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        //edtFechaInicio.setText(sdf.format(cFechaInicio.getTime()));
    }

}
