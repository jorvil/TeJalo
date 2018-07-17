package tejalo.com.pe;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Usuario;
import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.RestService.RestService;

public class PublicarViajeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String url = Globales.url;

    private Retrofit retrofit;
    private RestService restService;

    private Spinner spiOrigen;
    private ArrayAdapter<Distrito> adapterDistritoOrigen;
    private Long distritoOrigen;

    private Spinner spiDestino;
    private ArrayAdapter<Distrito> adapterDistritoDestino;
    private Long distritoDestino;

    private EditText edtFecha;
    private EditText edtCantidad;
    private EditText edtTarifa;

    private Button btnGrabar;

    final Calendar cFecha = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viaje);

        spiOrigen = findViewById(R.id.spiOrigen);
        spiDestino = findViewById(R.id.spiDestino);
        edtFecha = findViewById(R.id.edtFecha);
        edtCantidad = findViewById(R.id.edtCantidad);
        edtTarifa = findViewById(R.id.edtTarifa);
        btnGrabar = findViewById(R.id.btnGrabar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //

        spiOrigen.setOnItemSelectedListener(this);
        spiDestino.setOnItemSelectedListener(this);
        edtFecha.setOnClickListener(this);
        btnGrabar.setOnClickListener(this);

        listarDistrito();
        fechaActual();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGrabar:
                int validar;
                validar = validarCampos();
                if (validar == 1) {
                    grabarViaje();
                }
                break;
            case R.id.edtFecha:
                dateOnClickFecha(view);
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spiOrigen:

                Distrito origen = adapterDistritoOrigen.getItem(position);
                distritoOrigen = origen.getCodigo();
                break;
        }

        switch (parent.getId()) {
            case R.id.spiDestino:

                Distrito destino = adapterDistritoDestino.getItem(position);
                distritoDestino = destino.getCodigo();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void listarDistrito() {
        restService.listarDistrito().enqueue(new Callback<List<Distrito>>() {
            @Override
            public void onResponse(Call<List<Distrito>> call, Response<List<Distrito>> response) {
                List<Distrito> distritoList = response.body();
                //Origen
                adapterDistritoOrigen = new ArrayAdapter<Distrito>(getBaseContext(),
                        android.R.layout.simple_spinner_item, distritoList);
                adapterDistritoOrigen.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiOrigen.setAdapter(adapterDistritoOrigen);
                //Origen
                //Destino
                adapterDistritoDestino = new ArrayAdapter<Distrito>(getBaseContext(),
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

    private void grabarViaje() {

        Viaje viaje = new Viaje();

        Usuario usuario = new Usuario();
        Distrito origen = new Distrito();
        Distrito destino = new Distrito();

        usuario.setCodigo(Globales.usuario);
        origen.setCodigo(distritoOrigen);
        destino.setCodigo(distritoDestino);

        viaje.setFecha(edtFecha.getText().toString());
        viaje.setHora("09:00");
        viaje.setCantidad(Integer.valueOf(edtCantidad.getText().toString()));
        viaje.setDisponible(Integer.valueOf(edtCantidad.getText().toString()));
        viaje.setTarifa(Double.valueOf(edtTarifa.getText().toString()));
        viaje.setEstado("P");

        viaje.setUsuario(usuario);
        viaje.setOrigen(origen);
        viaje.setDestino(destino);

        restService.grabarViaje(viaje).enqueue(new Callback<Viaje>() {
            @Override
            public void onResponse(Call<Viaje> call, Response<Viaje> response) {

                if (response.isSuccessful()) {
                    //String mensaje = response.body().toString();
                    Toast.makeText(PublicarViajeActivity.this, "Viaje registrado correctamente", Toast.LENGTH_LONG).show();
                    Log.e("post submitted to API.", response.body().toString());

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Viaje> call, Throwable t) {
                //Log.e("RestService", "onFailure: ", t);
                Toast.makeText(PublicarViajeActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
            }
        });
    }

    DatePickerDialog.OnDateSetListener dateFecha = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cFecha.set(Calendar.YEAR, year);
            cFecha.set(Calendar.MONTH, monthOfYear);
            cFecha.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            fechaActual();
        }
    };

    public void dateOnClickFecha(View view) {
        new DatePickerDialog(this, dateFecha,
                cFecha.get(Calendar.YEAR), cFecha.get(Calendar.MONTH), cFecha.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void fechaActual() {
        String dateFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        edtFecha.setText(sdf.format(cFecha.getTime()));
    }

    public int validarCampos() {
        return 1;
    }

}
