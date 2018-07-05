package tejalo.com.pe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.model.Usuario;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {

    private Retrofit retrofit;
    private RestService restService;

    private Usuario usuario;

    private EditText edtNombre;
    private EditText edtApellido;
    private EditText edtDni;
    private EditText edtPassword;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private EditText edtTelefono;
    private EditText edtEmail;
    private TextView txtMensaje;

    private Button btnGrabar;
    private String mensaje;

    private String url = "http://192.168.43.116:8888/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtDni = findViewById(R.id.edtDni);
        edtPassword = findViewById(R.id.edtPassword);
        rbMasculino = findViewById(R.id.rbMasculino);
        rbFemenino = findViewById(R.id.rbFemenino);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtEmail = findViewById(R.id.edtEmail);
        txtMensaje = findViewById(R.id.txtMensaje);

        btnGrabar = findViewById(R.id.btnGrabar);

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RestService.class);

        btnGrabar.setOnClickListener(this);

    }

    private void grabarUsuario() {
        String sexo = "";

        Usuario usuario = new Usuario();
        usuario.setNombre(edtNombre.getText().toString());
        usuario.setApellido(edtApellido.getText().toString());
        usuario.setDni(edtDni.getText().toString());
        usuario.setPassword(edtPassword.getText().toString());
        if (rbMasculino.isChecked() == true) {
            sexo = "M";
        }
        if (rbFemenino.isChecked() == true) {
            sexo = "F";

        }

        usuario.setSexo(sexo);
        usuario.setTelefono(edtTelefono.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setEstado("A");

        restService.grabarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {
                    mensaje = response.body().toString();
                    txtMensaje.setText("Registro OK - " + mensaje.toString());
                    Log.e("post submitted to API.", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
                txtMensaje.setText(t.toString());
            }
        });
    }

    private int validarCampos() {

        int validar = 1;

        if (edtNombre.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su Nombre", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtApellido.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su Apellido", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtDni.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su DNI", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su Password", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su Password", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (rbMasculino.isChecked() == false && rbFemenino.isChecked() == false) {
            Toast.makeText(getApplication(), "Debe seleccionar su sexo", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtTelefono.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su Telefono", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        if (edtEmail.getText().toString().equals("")) {
            Toast.makeText(getApplication(), "Debe Ingresar su E-mail", Toast.LENGTH_LONG).show();
            validar = -1;

            return validar;
        }

        return validar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGrabar:
                int validar;
                validar = validarCampos();
                if (validar == 1) {
                    grabarUsuario();
                }
                break;
        }
    }

}
