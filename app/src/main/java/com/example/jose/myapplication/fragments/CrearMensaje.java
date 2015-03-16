package com.example.jose.myapplication.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.R.layout;
import android.widget.Toast;

import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CrearMensaje extends Fragment {
    JSONParser jParser = new JSONParser();
    public CrearMensaje() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_crear_mensaje, container, false);

        Button boton= (Button) rootView.findViewById(R.id.btnCrearPost);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crear post/solicitud de donante y guardar
                crearPost post= new crearPost();
                post.execute();
                /*despues del hilo poner lo que sigue en el onPostExecute del primer hilo*/
                //crear dialogo para edicion de post y funcion compartir
                /*en el onPostExecute del hilo de editar*/
                //Enviar al fragment home o bandeja o main activity
             /*   Intent i= new Intent(getActivity(), MainActivity.class);
                i.putExtra("pos",1);
                startActivity(i);*/
            }
        });

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner sp = (Spinner) getActivity().findViewById(R.id.spinnerCrearPost);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.grupo_sanguineo, layout.simple_spinner_item);
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),"Has seleccionado "+ parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> parentView){

            }
        });
    }

    private class crearPost extends AsyncTask<Void,Void,String>{
        JSONObject json=null;
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            EditText nombres= (EditText)getActivity().findViewById(R.id.crearPostNombres);
            EditText apellidos = (EditText)getActivity().findViewById(R.id.crearPostApellido);
            Spinner sp = (Spinner) getActivity().findViewById(R.id.spinnerCrearPost);
            EditText tel= (EditText)getActivity().findViewById(R.id.crearPostTel);
            String idUser = getActivity().getIntent().getStringExtra("MyID");
            Log.i("captura dato nombres",nombres.getText().toString());
            par.add(new BasicNameValuePair("nombres",nombres.getText().toString()));
            par.add(new BasicNameValuePair("apellidos",apellidos.getText().toString()));
            par.add(new BasicNameValuePair("tipoSangre",sp.getSelectedItem().toString()));
            par.add(new BasicNameValuePair("telefono",tel.getText().toString()));
            par.add(new BasicNameValuePair("idUser",idUser));
            Log.d("Mi id de usuario",idUser);
            Log.i("captura dato telefono",tel.getText().toString());
            try {
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/crear_post.php","GET",par);
                Log.d("Mi json 1:", json.toString());
                int success = json.getInt("success");
                if (success==1){
                    Log.d("","se guardo en la base de datos correctamente");
                }//verificar su conexion a internet*/
                Intent i= new Intent(getActivity(), PrincipalActivity.class);
                startActivity(i);
            }catch (Exception e){
              e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file){

        }
    }




}
