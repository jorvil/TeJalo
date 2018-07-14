package tejalo.com.pe;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tejalo.com.pe.Fragment.PerfilFragment;
import tejalo.com.pe.Fragment.ReservaFragment;
import tejalo.com.pe.Fragment.ViajeFragment;

public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout mDrawerLayout;
    private ListView mListLeft;
    private ActionBarDrawerToggle mToggle;

    private static final String[] OPTIONS = {
            "Mi Perfil",
            "Mis Viajes",
            "Mis Reservas",
            "Cerrar Sesion"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mListLeft = (ListView) findViewById(R.id.drawerList);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_activated_1, OPTIONS);
        mListLeft.setAdapter(adapter);
        mListLeft.setChoiceMode(mListLeft.CHOICE_MODE_SINGLE);
        mListLeft.setOnItemClickListener(this);

        //CodigoUsuario = getIntent().getStringExtra(EXTRA_CODIGO_USUARIO);

        SetContent(0);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        SetContent(position);
    }

    private Fragment getFragment(int position) {
        Fragment f = null;

        switch (position) {
            case 0: //Mi Perfil
                f = new PerfilFragment();
                break;

            case 1: //Mi Viajes
                f = new ViajeFragment();
                break;

            case 2: //Mi Reservas
                f = new ReservaFragment();
                break;

            case 3: //Cerrar Sesion
                cerrarSesion();
                break;
        }

        return f;
    }

    private void SetContent(int position) {
        Fragment f = getFragment(position);

        if (f != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, f);
            ft.commit();
            mListLeft.setItemChecked(position, true);
        }
        mDrawerLayout.closeDrawer(mListLeft);
    }

    private void cerrarSesion() {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

        finish();
    }


}
