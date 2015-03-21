package com.example.jose.myapplication;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.jose.myapplication.adapters.MenuDrawerListAdapter;
import com.example.jose.myapplication.adapters.TabsPagerAdapter;
import com.example.jose.myapplication.fragments.BandejaFragment;
import com.example.jose.myapplication.fragments.Cita1Fragment;

import com.example.jose.myapplication.fragments.CitaFragment;
import com.example.jose.myapplication.fragments.CrearMensaje;
import com.example.jose.myapplication.fragments.ListaContactoFragment;
import com.example.jose.myapplication.fragments.PerfilFragment;
import com.example.jose.myapplication.models.MenuDrawerItem;

import android.support.v4.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PrincipalActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    private String[] tabs = { "Paso 1", "Paso 2", "Finalizar" };
    /**/
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
        titleFragment.setText("Lista de Contactos");
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //fragment lista de contactos
            Log.d("entro","null");
            displayView(5);
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

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
        viewPager = (ViewPager) findViewById(R.id.mi_pager);
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:

               // viewPager.removeAllViews();//no found
                viewPager.setVisibility(View.INVISIBLE);
              /*  if (mAdapter!=null){
                    mAdapter=null;
                    mAdapter.notifyDataSetChanged();
                }*/
                fragment = new BandejaFragment();
                break;
            case 1:
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new CrearMensaje();
                break;
            case 2:
                fragment = new CitaFragment();
                //citas
                break;
            case 3:
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new PerfilFragment();
                break;
            case 4:
                viewPager.setVisibility(View.INVISIBLE);
                System.exit(0);
                break;
            case 5:
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new ListaContactoFragment();
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
        if(position<=4) {
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            //setTitle(navMenuTitles[position]);
            titleFragment.setText(navMenuTitles[position]);
            titleFragment.setText("Cita para donar");
            mDrawerLayout.closeDrawer(mDrawerList);
            //mDrawerLayout.closeDrawers();
        }
          if(position==2){


              actionBar =  getSupportActionBar();
              mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

              viewPager.setAdapter(mAdapter);
              actionBar.setHomeButtonEnabled(false);
              actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

              // Adding Tabs
              for (String tab_name : tabs) {
                  actionBar.addTab(actionBar.newTab().setText(tab_name)
                          .setTabListener(this));
              }

              /**
               * on swiping the viewpager make respective tab selected
               * */
              viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                  @Override
                  public void onPageSelected(int position) {
                      // on changing the page
                      // make respected tab selected
                      actionBar.setSelectedNavigationItem(position);
                  }

                  @Override
                  public void onPageScrolled(int arg0, float arg1, int arg2) {
                  }

                  @Override
                  public void onPageScrollStateChanged(int arg0) {
                  }
              });
          }else{
              actionBar =  getSupportActionBar();
              actionBar.removeAllTabs();
              actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);


          }
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
