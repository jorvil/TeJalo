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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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

    private String url = "http://192.168.137.2:8888/";
    //private String url="http://intranet.fridaysperu.com:8888/";

    private Retrofit retrofit;
    private RestService restService;

    private Button btnPublicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viaje, container, false);


        btnPublicar = view.findViewById(R.id.btnPublicar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //

        btnPublicar.setOnClickListener(this);

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

    public void abrirViaje() {
        Intent intent = new Intent(getActivity(), PublicarViajeActivity.class);
        startActivity(intent);
    }
}
