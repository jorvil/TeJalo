package tejalo.com.pe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.RestService.RestService;
import tejalo.com.pe.Model.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private String url = Globales.url;

    private Retrofit retrofit;
    private RestService restService;

    private EditText edtNombre;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNombre = findViewById(R.id.edtNombre);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtCrear = findViewById(R.id.txtCrear);

        txtCrear.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
        //
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtCrear:
                openRegistrarUsuario();
                break;
            case R.id.btnLogin:
                loguear(edtNombre.getText().toString(), edtPassword.getText().toString());
                break;
        }
    }

    private void openRegistrarUsuario() {
        Intent intent = new Intent(getBaseContext(), RegistrarUsuarioActivity.class);
        startActivity(intent);
    }

    private void openActivity() {
        Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private void loguear(String nombre, String password) {
        restService.loguear(nombre, password).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                Usuario usuario = response.body();

                if (usuario == null) {

                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG).show();

                } else {

                    Globales.usuario = usuario.getCodigo();
                    openActivity();

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //Log.e("RestService", "onFailure: ", t);
                Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

}
