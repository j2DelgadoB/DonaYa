package com.example.jose.myapplication;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jose.myapplication.adapters.MenuDrawerListAdapter;
import com.example.jose.myapplication.fragments.BandejaFragment;
import com.example.jose.myapplication.fragments.CrearMensaje;
import com.example.jose.myapplication.fragments.LoginFragment;
import com.example.jose.myapplication.models.MenuDrawerItem;

import android.support.v4.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class PrincipalActivity extends ActionBarActivity {
    private Fragment[] fragments = new Fragment[]{
    new BandejaFragment(),
    new CrearMensaje(),
    };


    private TextView titleFragment;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    ArrayList<MenuDrawerItem> navDrawerItems;
    private MenuDrawerListAdapter adapter;
    private ListView mDrawerList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_home);
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems  = new ArrayList<MenuDrawerItem>();

        //Bandeja
        navDrawerItems.add(new MenuDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, "3" ));
        //Solicitar donante
        navDrawerItems.add(new MenuDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        //Cita para donar
        navDrawerItems.add(new MenuDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        //Perfil
        navDrawerItems.add(new MenuDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        //Salir app
        navDrawerItems.add(new MenuDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();
        // setting the nav drawer list adapter
        adapter = new MenuDrawerListAdapter(getApplicationContext(),
                navDrawerItems);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name){
            public  void onDraweClosed(View view){
                Log.d("Cerrado", "!!");
            }
            public void onDrawerOpened(View drawer){
                Log.d("Apertura completado","!!");
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        titleFragment=(TextView)findViewById(R.id.titulo_fragmento);
        titleFragment.setText("Bandeja");
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

    }
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            Log.d("logclicmenu","entro aqui");
            displayView(position);
        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BandejaFragment();
                break;
            case 1:
                fragment = new CrearMensaje();
                break;
            case 2:
                Log.d("2","Aun falta implementar");
                break;
            case 3:
                Log.d("3", "falta implementar");
                break;
            case 4:
                System.exit(0);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
           mDrawerList.setItemChecked(position, true);
           mDrawerList.setSelection(position);
           //setTitle(navMenuTitles[position]);
          titleFragment.setText(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            //mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.closessesion).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

}
