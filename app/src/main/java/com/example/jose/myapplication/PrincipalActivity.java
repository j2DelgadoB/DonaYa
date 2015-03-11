package com.example.jose.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.jose.myapplication.fragments.BandejaFragment;
import com.example.jose.myapplication.fragments.CrearMensaje;
import com.example.jose.myapplication.fragments.LoginFragment;


public class PrincipalActivity extends ActionBarActivity {
    private Fragment[] fragments = new Fragment[]{
    new BandejaFragment(),
    new CrearMensaje(),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        for(Fragment fragment: fragments){
            fragmentTransaction.add(R.id.main_home,fragment).hide(fragment);

        }
       /*
        String posi=this.getIntent().getStringExtra("pos");
        if(posi==null){
            //default
            */
            fragmentTransaction.show(fragments[0]).commit();//0
        /*}else{
            int p= Integer.parseInt(posi);
            fragmentTransaction.show(fragments[p]).commit();
        }*/



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Boton de cerrar sesión
            case R.id.closessesion:
                //Borramos el usuario almacenado en preferencias y volvemos a la pantalla de login
                SharedPreferences settings = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                //Confirmamos el almacenamiento.
                editor.commit();
                //Volvemos a la pantalla de Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
