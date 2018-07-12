package tejalo.com.pe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tejalo.com.pe.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RestService restService;

    private ArrayAdapter<Usuario> adapter;

    //private String url = "http://192.168.137.2:8888/";
    private String url="http://intranet.fridaysperu.com:8888/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<Usuario>(
                this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restService = retrofit.create(RestService.class);

        listarUSuarios();

    }

    private void listarUSuarios() {
        restService.listarUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> usuarioList = response.body();
                adapter.clear();
                adapter.addAll(usuarioList);
                Log.d("RestService", "onResponse: " + usuarioList.size());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("RestService", "onFailure: ", t);
            }
        });
    }

}
