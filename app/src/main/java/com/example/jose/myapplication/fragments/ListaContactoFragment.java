package com.example.jose.myapplication.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 18/03/2015.
 */
public class ListaContactoFragment extends Fragment {
    JSONParser jParser = new JSONParser();
    JSONArray ContactListJson = null;
    ArrayList<String> contactListName = new ArrayList<String>();//para el listview
   // ArrayList<HashMap<String, String>> contactList= new ArrayList<HashMap<String, String>>();
    public ListaContactoFragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_contacto, container, false);
        Contactos contactos = new Contactos();
        contactos.execute();
        return rootView;
    }
    private class Contactos extends AsyncTask<Void,Void,String> {
        JSONObject json=null;

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            String id=getActivity().getIntent().getStringExtra("MyID");//cuando pasa de login -->pintar lista bien pero cuando pasa de editar perfil --> pintar lista no anda
            Log.d("mi id:",id);
            par.add(new BasicNameValuePair("idUser",id));
            try {
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/mostrar_all_contacts.php","POST",par);
                Log.d("mi json 2", json.toString());
                int success=json.getInt("success");
                if (success==1){
                    ContactListJson = json.getJSONArray("ContactList");
                    for (int i=0; i< ContactListJson.length();i++){
                        JSONObject c = ContactListJson.getJSONObject(i);
                        Log.d("amigo",c.getString("nomAmigo"));
                        String nombreAmigo= c.getString("nomAmigo");
                        String apellidoAmigo = c.getString("apeAmigo");
                        contactListName.add(nombreAmigo + " " + apellidoAmigo);
                    }
                }

            }catch (Exception e){

            }
                return null;
        }
        protected void onPostExecute(String file_url) {
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),R.layout.contact_list_item,R.id.name_contact, contactListName);
            ListView lista_contactos=(ListView)getActivity().findViewById(R.id.listaContacto);
            lista_contactos.setAdapter(adaptador);
        }
    }
}
