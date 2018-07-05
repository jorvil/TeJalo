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
import tejalo.com.pe.model.Usuario;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Retrofit retrofit;
    private RestService restService;

    private EditText edtNombre;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtCrear;

    //private String url = "http://192.168.43.116:8888/";
    private String url="http://intranet.fridaysperu.com:8888/";

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

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RestService.class);
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
        Intent intent = new Intent(getBaseContext(), RegistrarUsuario.class);
        startActivity(intent);
    }

    private void openActivity() {
        Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private void loguear(String nombre, String password) {
        restService.loguear(nombre, password).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                int resultado;

                List<Usuario> usuarioList = response.body();

                if (usuarioList == null) {

                    Toast.makeText(getApplication(), "*** Usuario no existe ***", Toast.LENGTH_LONG).show();

                } else {

                    resultado = usuarioList.size();

                    if (resultado > 0) {
                        openActivity();
                    } else {
                        Toast.makeText(getApplication(), "Usuario no existe o datos mal ingresados", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });
    }

}
