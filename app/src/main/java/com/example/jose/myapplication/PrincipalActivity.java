package com.example.jose.myapplication;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
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


import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jose.myapplication.adapters.MenuDrawerListAdapter;
import com.example.jose.myapplication.adapters.TabsPagerAdapter;
import com.example.jose.myapplication.fragments.BandejaFragment;

import com.example.jose.myapplication.fragments.CitaFragment;
import com.example.jose.myapplication.fragments.CrearMensaje;
import com.example.jose.myapplication.fragments.HeaderFragment;
import com.example.jose.myapplication.fragments.ListaContactoFragment;
import com.example.jose.myapplication.fragments.PerfilFragment;
import com.example.jose.myapplication.models.MenuDrawerItem;
import com.example.jose.myapplication.models.Perfil;
import com.example.jose.myapplication.utils.JSONParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.AddFloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class PrincipalActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ArrayList<String> list_contacts=new ArrayList<String>();

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

    AddFloatingActionButton buttonLoadFoto,buttonLoadFondo;
    Button upload;

    private Context context;
    private Activity actividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonLoadFoto=(AddFloatingActionButton) findViewById(R.id.buttonLoadPicture);
        buttonLoadFondo=(AddFloatingActionButton)findViewById(R.id.buttonLoadPicture2);
        upload=(Button)findViewById(R.id.upload);
        setContentView(R.layout.activity_main);
        //context=this;
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        actividad=this;



/*
        Picasso.with(this)
                .load(this.getIntent().getStringExtra("Photo"))
                .into(photo);*/

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



        if (savedInstanceState == null) {
            titleFragment=(TextView)findViewById(R.id.titulo_fragmento);
            titleFragment.setText("Lista de Contactos");
            // on first time display view for first nav item

            //fragment lista de contactos

            //CargarPerfil cp=new CargarPerfil(actividad);
            //cp.execute();
            Log.d("entro","null");
            //Picasso.with(actividad).load(urlFoto).into(photo);
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

    public void displayView(int position) {

        viewPager = (ViewPager) findViewById(R.id.mi_pager);
        // update the main content by replacing fragments
        Fragment fragment = null;
        CargarImagen cargarImagen=null;



        switch (position) {
            case 0:

                cargarImagen= new CargarImagen(0);
                cargarImagen.execute();
                // viewPager.removeAllViews();//no found
                viewPager.setVisibility(View.INVISIBLE);
              /*  if (mAdapter!=null){
                    mAdapter=null;
                    mAdapter.notifyDataSetChanged();
                }*/
                Log.d("Bandeja:","entro");
                fragment = new BandejaFragment();
                fragment.setArguments(getIntent().getExtras());
                break;
            case 1:

                cargarImagen= new CargarImagen(0);
                cargarImagen.execute();
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new CrearMensaje();
                fragment.setArguments(getIntent().getExtras());
                break;
            case 2:

                cargarImagen= new CargarImagen(0);
                cargarImagen.execute();
                viewPager.setVisibility(View.VISIBLE);
                fragment = new CitaFragment();
                fragment.setArguments(getIntent().getExtras());
                //citas
                break;
            case 3:
                cargarImagen= new CargarImagen(1);
                cargarImagen.execute();
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new PerfilFragment();
                fragment.setArguments(getIntent().getExtras());
                break;
            case 4:
                cargarImagen= new CargarImagen(0);
                cargarImagen.execute();
                viewPager.setVisibility(View.INVISIBLE);
                System.exit(0);
                break;
            case 5:
                cargarImagen= new CargarImagen(0);
                cargarImagen.execute();
                viewPager.setVisibility(View.INVISIBLE);
                fragment = new ListaContactoFragment();
                fragment.setArguments(getIntent().getExtras());
            case 6:
               /* viewPager.setVisibility(View.INVISIBLE);
                fragment = new PerfilFragment();
                fragment.setArguments(getIntent().getExtras());*/
                break;
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
            Log.d("MainActivity", "Error in creating fragment");
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
    private class CargarImagenEdit extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {


            try {
                FrameLayout frameEdit= (FrameLayout) findViewById(R.id.frame_images_edit);
                frameEdit.setVisibility(View.VISIBLE);
                HeaderFragment headerFragment = new HeaderFragment(1);
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("headerEdit")
                        .replace(R.id.frame_images_edit,headerFragment)
                        .commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        /*

         */
        protected void onPostExecute(String file_url) {


            Log.d("termino","hilo borrado");



        }
    }
    //
    private class CargarImagen extends AsyncTask<Void,Void,String> {
        int perfilEnable=0;
        CargarImagen(int perfilEnable){
            this.perfilEnable=perfilEnable;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                //Load header
                HeaderFragment headerFragment = new HeaderFragment(perfilEnable);
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("header")
                        .replace(R.id.frame_images,headerFragment)
                        .commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        /*

         */
        protected void onPostExecute(String file_url) {
            //


           //displayView(5);


            //  Picasso.with(context).load(urlFoto).error(R.drawable.user).placeholder(R.drawable.user).into(photo);
            //  Picasso.with(context).load(urlFondo).error(R.drawable.header_bg).placeholder(R.drawable.header_bg).into(background);
        }
    }


    ProgressDialog prgDialog;
    String encodedString=null,encodedString2=null;
    RequestParams params = new RequestParams();
    String imgPath=null,imgPath2=null, fileName=null, fileName2=null;
    Bitmap bitmap,bitmap2;
    private static int RESULT_LOAD_IMG = 1;
    private String tipo="foto";
    private String carpetaUsername;

    public void loadImagefromGallery(View view) {
        tipo="foto";
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }
    public void loadImagefromGallery2(View view) {
        tipo="fondo";
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            CargarPerfil cp= new CargarPerfil();
            cp.execute();
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                ImageView imgView;
                if(tipo.equals("foto")){
                    imgView = (ImageView) findViewById(R.id.imageFoto);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView

                    imgView.setImageBitmap(BitmapFactory
                            .decodeFile(imgPath));
                    // Get the Image's file name
                    String fileNameSegments[] = imgPath.split("/");
                    fileName = fileNameSegments[fileNameSegments.length - 1];
                }else{
                    imgView = (ImageView) findViewById(R.id.imageFondo);
                    imgPath2 = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView
                    imgView.setImageBitmap(BitmapFactory
                            .decodeFile(imgPath2));
                    // Get the Image's file name
                    String fileNameSegments[] = imgPath2.split("/");
                    fileName2 = fileNameSegments[fileNameSegments.length - 1];
                }

                // Put file name in Async Http Post Param which will used in Php web app
                if (fileName!=null)
                    params.put("filename", fileName);
                if (fileName2!=null)
                    params.put("filenameFondo", fileName2);

                params.put("username",carpetaUsername);
                params.put("idUser",getIntent().getStringExtra("MyID"));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // When Upload button is clicked
    public void uploadImage(View v) {
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            prgDialog.setMessage("Converting Image to Binary Data");
            prgDialog.show();
            // Convert image to String using Base64
            encodeImagetoString();
            // When Image is not selected from Gallery
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                if(imgPath!=null) {
                    bitmap = BitmapFactory.decodeFile(imgPath,
                            options);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Must compress the Image to reduce image size to make upload easy
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] byte_arr = stream.toByteArray();
                    // Encode Image to String
                    encodedString = Base64.encodeToString(byte_arr, 0);
                }
                if(imgPath2!=null) {
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bitmap2 = BitmapFactory.decodeFile(imgPath2, options);
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 50, stream2);
                    byte[] byte_arr2 = stream2.toByteArray();
                    encodedString2 = Base64.encodeToString(byte_arr2, 0);
                }

                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                if (encodedString!=null)
                    params.put("image", encodedString);
                if (encodedString2!=null)
                    params.put("imageFondo",encodedString2);

                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // http://192.168.2.4:9000/imgupload/upload_image.php
    // http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("Invoking Php");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://isulamotors.com.pe/SoyDonante/upload_image.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }
    private class CargarPerfil extends AsyncTask<Void,Void,String> {
        JSONObject json = null;
        JSONParser jParser = new JSONParser();
        JSONArray PerfilListJson = null;
        ArrayList<Perfil> perfilList= new ArrayList<Perfil>();
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("idUser", getIntent().getStringExtra("MyID")));
            try {
                //json = jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/get_perfil.php", "POST", par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/get_perfil.php","POST",par);
                Log.d("mi json cargar perfil", json.toString());
                int success = json.getInt("success");
                if (success == 1) {
                    PerfilListJson = json.getJSONArray("dataPerfil");
                    for (int i = 0; i < PerfilListJson.length(); i++) {
                        JSONObject c = PerfilListJson.getJSONObject(i);
                        Log.d("nom Perfil", c.getString("nombres"));
                        carpetaUsername= c.getString("username");
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    }
    private void setButtonLoader(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float dp = 175f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (metrics.density * dp + 0.5f);


        FrameLayout images= (FrameLayout) findViewById(R.id.frame_images);

        images.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,pixels));
    }

}
