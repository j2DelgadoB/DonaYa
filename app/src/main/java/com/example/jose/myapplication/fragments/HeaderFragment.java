package com.example.jose.myapplication.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.adapters.RecyclerHolderHeaderAdapter;
import com.example.jose.myapplication.models.HeaderItem;
import com.example.jose.myapplication.models.Perfil;
import com.example.jose.myapplication.utils.JSONParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 22/04/2015.
 */
public class HeaderFragment  extends Fragment {
    private String urlFondo,urlFoto;
    ImageView photo,background;
    ArrayList<HeaderItem> list_header = new ArrayList<HeaderItem>();
    private int visibilidad=0;
    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    public HeaderFragment(){

    }
    public HeaderFragment(int buttonsLoaderEdit){
        visibilidad=buttonsLoaderEdit;
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fondo_foto, container, false);
        prgDialog = new ProgressDialog(getActivity());
        // Set Cancelable as False
        prgDialog.setCancelable(false);


        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CargarHeader cargarHeader=new CargarHeader();
        cargarHeader.execute();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_fondo_foto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerHolderHeaderAdapter(visibilidad,list_header, R.layout.item_fondo_foto,getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public class CargarHeader extends AsyncTask<Void,Void,String> {
        JSONObject json1 = null;
        ArrayList<Perfil> perfilList= new ArrayList<Perfil>();
        Activity activity;
        JSONParser jParser = new JSONParser();
        private JSONArray PerfilArrayJson = null;
        private String urlFondo,urlFoto;
        ArrayList<HeaderItem> list_header= new ArrayList<HeaderItem>();
        ArrayList<HeaderItem> list_header_fondo= new ArrayList<HeaderItem>();
        NetworkImageView webImagenFondo;
        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> par = new ArrayList<NameValuePair>();

            par.add(new BasicNameValuePair("idUser", getActivity().getIntent().getStringExtra("MyID")));
            try {
                webImagenFondo=(NetworkImageView) getActivity().findViewById(R.id.imageFondo);
               // json1 = jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/get_perfil.php", "POST", par);
                json1=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/get_perfil.php","POST",par);
                Log.d("mi json cargar imagen", json1.toString());
                int success = json1.getInt("success");
                if (success == 1) {
                    PerfilArrayJson = json1.getJSONArray("dataPerfil");
                    for (int i = 0; i < PerfilArrayJson.length(); i++) {
                        JSONObject c = PerfilArrayJson.getJSONObject(i);
                        Log.d("nom Perfil", c.getString("nombres"));
                        // carpetaUsername= c.getString("username");
                        //urlFondo=c.getString("fondo").replace("\\", "");
                        urlFoto=c.getString("foto").replace("\\", "");
                        urlFondo=c.getString("fondo").replace("\\","");
                        Log.d("Mi foto",urlFoto);
                        Log.d("Mi fondo",urlFondo);

                        HeaderItem headerItem = new HeaderItem();
                        headerItem.setUrlFoto(urlFoto);
                        headerItem.setUrlFondo(urlFondo);
                        list_header.add(headerItem);

                        //background = (ImageView) findViewById(R.id.imageView2);


                        //Picasso.with(actividad).load(urlFondo).placeholder(R.drawable.header_bg).into(background);

                    }
                    /*urlFoto="https://graph.facebook.com/867707063286892/picture?type=large";
                    HeaderItem headerItem = new HeaderItem();
                    headerItem.setUrlFoto(urlFoto);
                    list_header.add(headerItem);*/

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        /*

         */
        protected void onPostExecute(String file_url) {

            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_fondo_foto);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new RecyclerHolderHeaderAdapter(visibilidad,list_header, R.layout.item_fondo_foto,getActivity()));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //displayView(5);
           //

            //  Picasso.with(context).load(urlFoto).error(R.drawable.user).placeholder(R.drawable.user).into(photo);
            //  Picasso.with(context).load(urlFondo).error(R.drawable.header_bg).placeholder(R.drawable.header_bg).into(background);
        }
    }




}
