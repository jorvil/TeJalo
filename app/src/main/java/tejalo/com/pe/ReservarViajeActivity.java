package tejalo.com.pe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Spinner;
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
import tejalo.com.pe.Adapter.ViajeListadoPasajeroAdapter;
import tejalo.com.pe.Model.Distrito;
import tejalo.com.pe.Model.Reserva;
import tejalo.com.pe.Model.Usuario;
import tejalo.com.pe.Model.Viaje;
import tejalo.com.pe.RestService.RestService;

public class ReservarViajeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private String url = Globales.url;

    private Spinner spiOrigen;
    private ArrayAdapter<Distrito> adapterDistritoOrigen;
    private Long distritoOrigen;

    private Spinner spiDestino;
    private ArrayAdapter<Distrito> adapterDistritoDestino;
    private Long distritoDestino;

    private Retrofit retrofit;
    private RestService restService;

    private EditText edtFecha;
    private ListView listViaje;
    private Button btnBuscar;

    final Calendar cFecha = Calendar.getInstance();

    List<Viaje> viajeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_viaje);

        spiOrigen = findViewById(R.id.spiOrigen);
        spiDestino = findViewById(R.id.spiDestino);
        edtFecha = findViewById(R.id.edtFecha);
        listViaje = findViewById(R.id.listViaje);
        btnBuscar = findViewById(R.id.btnBuscar);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //

        spiOrigen.setOnItemSelectedListener(this);
        spiDestino.setOnItemSelectedListener(this);

        listViaje.setOnItemClickListener(this);

        edtFecha.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

        listarDistrito();
        fechaActual();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBuscar:
                listarViajexPasajero(distritoOrigen, distritoDestino, edtFecha.getText().toString(),Globales.usuario);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Long viaje;

        viaje = viajeList.get(position).getIdViaje();
        grabarReserva(viaje);
    }

    public void listarViajexPasajero(Long distritoOrigen, Long distritoDestino, String fecha, Long codigo) {
        restService.listarViajexPasajero(distritoOrigen, distritoDestino, fecha, codigo).enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                int resultado;

                viajeList = response.body();

                if (viajeList == null) {

                    Toast.makeText(getApplicationContext(), "*** Viajes no encontrados ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = viajeList.size();

                    if (resultado > 0) {
                        ViajeListadoPasajeroAdapter adapter = new ViajeListadoPasajeroAdapter(getBaseContext(), viajeList);
                        listViaje.setAdapter(adapter);
                    } else {
                        listViaje.setAdapter(null);
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
                adapterDistritoOrigen = new ArrayAdapter<Distrito>(getBaseContext(),
                        android.R.layout.simple_spinner_item, distritoList);
                adapterDistritoOrigen.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiOrigen.setAdapter(adapterDistritoOrigen);
                //Destino
                adapterDistritoDestino = new ArrayAdapter<Distrito>(getBaseContext(),
                        android.R.layout.simple_spinner_item, distritoList);
                adapterDistritoDestino.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spiDestino.setAdapter(adapterDistritoDestino);

                Log.d("RestService", "onResponse: " + distritoList.size());

            }

            @Override
            public void onFailure(Call<List<Distrito>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
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

    public void grabarReserva(final Long idViaje) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("TeJalo");
        alertDialogBuilder.setMessage("¿ Desea confirmar su Reserva ?");

        alertDialogBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //
                Reserva reserva = new Reserva();

                Usuario usuario = new Usuario();
                usuario.setCodigo(Globales.usuario);

                Viaje viaje = new Viaje();
                viaje.setIdViaje(idViaje);

                reserva.setEstado("P");
                reserva.setFecha("16/07/2018");
                reserva.setHora("09:00");

                reserva.setUsuario(usuario);
                reserva.setViaje(viaje);

                restService.grabarReserva(reserva).enqueue(new Callback<Reserva>() {
                    @Override
                    public void onResponse(Call<Reserva> call, Response<Reserva> response) {

                        if (response.isSuccessful()) {
                            //String mensaje = response.body().toString();
                            //Log.e("post submitted to API.", response.body().toString());
                            Toast.makeText(ReservarViajeActivity.this, "Reserva registrada correctamente", Toast.LENGTH_LONG).show();
                            //
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                            //

                        }
                    }

                    @Override
                    public void onFailure(Call<Reserva> call, Throwable t) {
                        //txtMensaje.setText(t.toString());
                        //Log.e("RestService", "onFailure: ", t);
                        Toast.makeText(ReservarViajeActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                    }
                });

                //
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(ReservarViajeActivity.this, "NO", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}

