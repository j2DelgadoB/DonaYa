package com.example.jose.myapplication.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.models.Perfil;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 20/03/2015.
 */
public class PerfilFragment extends Fragment{
    JSONParser jParser = new JSONParser();
    JSONArray PerfilListJson = null;
    ArrayList<Perfil> perfilList= new ArrayList<Perfil>();//para el listview

    EditText nombres,apellidos,tipoSangre,email,telefono, cuentaFace,cuentaTwitt,cuentaGoogle;

    ArrayAdapter adapter;
    // ArrayList<HashMap<String, String>> contactList= new ArrayList<HashMap<String, String>>();
    public PerfilFragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_perfil, container, false);
        //findview tipoSangre

        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Spinner tipoSangre=(Spinner) getActivity().findViewById(R.id.editPerfilTipoSangre);
        adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.grupo_sanguineo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSangre.setAdapter(adapter);
        CargarPerfil cp= new CargarPerfil();
        cp.execute();
        Button btnEditPerfil = (Button) getActivity().findViewById(R.id.btnPerfilSave);
        btnEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil ep= new EditarPerfil();
                ep.execute();
            }
        });
    }

    private class CargarPerfil extends AsyncTask<Void,Void,String> {
        JSONObject json = null;

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("idUser",getActivity().getIntent().getStringExtra("MyID")));
            try {
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/get_perfil.php","POST",par);
                Log.d("mi json cargar perfil", json.toString());
                int success=json.getInt("success");
                if (success==1){
                    PerfilListJson = json.getJSONArray("dataPerfil");
                    for (int i=0; i< PerfilListJson.length();i++){
                        JSONObject c = PerfilListJson.getJSONObject(i);
                        Log.d("nom Perfil",c.getString("nombres"));
                        Perfil perfil = new Perfil();
                        perfil.setNombres(c.getString("nombres"));
                        perfil.setApellidos(c.getString("apellidos"));
                        perfil.setTipoSangre(c.getString("tipoSangre"));
                        perfil.setEmail(c.getString("email"));
                        perfil.setTelefono(c.getString("telefono"));
                        perfil.setCuentaFace(c.getString("cuentaFace"));
                        perfil.setCuentaTwitt(c.getString("cuentaTwitt"));
                        perfil.setCuentaGoogle(c.getString("cuentaGoogle"));
                        perfil.setFondo(c.getString("fondo"));
                        perfil.setFoto(c.getString("foto"));
                        perfilList.add(perfil);
                    }
                }

            }catch (Exception e){

            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            //findviews 5
            EditText nombres= (EditText)getActivity().findViewById(R.id.editPerfilnom);
            EditText apellidos=(EditText) getActivity().findViewById(R.id.editPerfilape);
            Spinner tipoSangre=(Spinner) getActivity().findViewById(R.id.editPerfilTipoSangre);
            EditText email=(EditText) getActivity().findViewById(R.id.editPerfilEmail);
            EditText telefono=(EditText) getActivity().findViewById(R.id.editPerfilTel);

            nombres.setText(perfilList.get(0).getNombres());
            apellidos.setText(perfilList.get(0).getApellidos());
            int spinnerPosition = adapter.getPosition(perfilList.get(0).getTipoSangre());
            tipoSangre.setSelection(spinnerPosition);
            email.setText(perfilList.get(0).getEmail());
            telefono.setText(perfilList.get(0).getTelefono());
        }
    }
    private class EditarPerfil extends AsyncTask<Void,Void,String> {
        JSONObject json = null;

        @Override
        protected String doInBackground(Void... params) {
            EditText nombres= (EditText)getActivity().findViewById(R.id.editPerfilnom);
            EditText apellidos=(EditText) getActivity().findViewById(R.id.editPerfilape);
            Spinner tipoSangre=(Spinner) getActivity().findViewById(R.id.editPerfilTipoSangre);
            EditText email=(EditText) getActivity().findViewById(R.id.editPerfilEmail);
            EditText telefono=(EditText) getActivity().findViewById(R.id.editPerfilTel);

            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("idUser",getActivity().getIntent().getStringExtra("MyID")));
            par.add(new BasicNameValuePair("nombres",nombres.getText().toString()));
            par.add(new BasicNameValuePair("apellidos",apellidos.getText().toString()));
            par.add(new BasicNameValuePair("tipoSangre",tipoSangre.getSelectedItem().toString()));
            par.add(new BasicNameValuePair("email",email.getText().toString()));
            par.add(new BasicNameValuePair("telefono",telefono.getText().toString()));
            try {
                jParser = new JSONParser();
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/update_perfil.php","GET",par);
                Log.d("mi json update perfil", json.toString());
                int success=json.getInt("success");
                if (success==1){
                Log.d("valores modificados:","guardados correctamente");
                }

            }catch (Exception e){

            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            Intent i = new Intent(getActivity(), PrincipalActivity.class);
            i.putExtra("MyID",getActivity().getIntent().getStringExtra("MyID"));
            i.putExtra("MyUsername",getActivity().getIntent().getStringExtra("MyUsername"));
            i.putExtra("MyEmail",getActivity().getIntent().getStringExtra("MyEmail"));
            startActivity(i);
        }
    }
}
