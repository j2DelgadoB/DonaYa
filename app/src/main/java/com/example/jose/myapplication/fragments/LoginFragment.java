package com.example.jose.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 09/10/2014.
 */


public class LoginFragment extends Fragment{
    EditText user,pass;
    JSONParser jParser = new JSONParser();
    JSONArray usuarioJson= null;
    private static final String TAG_SUCCESS="success";
    private static final String TAG_USUARIO="usuario";
    private static final String TAG_ID="id";
    private static final String TAG_EMAIL="email";
    private static final String TAG_USERNAME="user";
    private static final String TAG_PASS="pass";
    static String username,password,ID,EMAIL;


    public LoginFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        user = (EditText)rootView.findViewById(R.id.editText);
        pass = (EditText)rootView.findViewById(R.id.editText2);

        //Recuperamos las preferencias almacenadas
        SharedPreferences prefs = this.getActivity().getSharedPreferences("MisPreferencias", getActivity().getApplicationContext().MODE_PRIVATE);
        String name_cache = prefs.getString("username", "");
        String pass_cache = prefs.getString("password", "");

        //Comprobamos nombre y clave de ususario
        if (name_cache.equals(username) && pass_cache.equals(password)) {
            //Si el usuario almacenado es correcto, entramos en la app
            Intent intent = new Intent(getActivity(), PrincipalActivity.class);
            startActivity(intent);
        }

        Button boton=(Button)rootView.findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Intent i=new Intent(getActivity() ,Empresa.class);
                //startActivity(i);
                Login login=new Login();
                login.execute();
            }
        });
        return rootView;
    }
    private class Login extends AsyncTask<Void,Void,String>{
        String pasaParametro;
        JSONObject json=null;

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("ausuario",user.getText().toString()));
            par.add(new BasicNameValuePair("acontrasena",pass.getText().toString()));
            try{
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/login.php","POST",par);
                //json=jParser.makeHttpRequest("http://isulamotors.com.pe/ventas/login.php","POST",par);

                Log.d("mi json ",json.toString());
                int success=json.getInt(TAG_SUCCESS);
                if (success==1){
                    usuarioJson = json.getJSONArray(TAG_USUARIO);
                    //aunque es un array de 1 fila lo recorremos
                    JSONObject c= usuarioJson.getJSONObject(0);
                    username= c.getString(TAG_USERNAME);
                    password= c.getString(TAG_PASS);
                    ID = c.getString(TAG_ID);
                    EMAIL = c.getString(TAG_EMAIL);

                    //login + cache
                    if(user.getText().toString().equals(username) && pass.getText().toString().equals(password)){
                        SharedPreferences settings= getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("username", user.getText().toString());
                        editor.putString("password",pass.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                        intent.putExtra("MyID",ID);
                        intent.putExtra("MyUsername",username);
                        intent.putExtra("MyEmail",EMAIL);
                        startActivity(intent);

                    }

                }
            }catch (Exception e){}
            return null;
        }
        protected  void onPostExecute(String file_url) {
            // Toast.makeText(getActivity(),"mi json " ,Toast.LENGTH_LONG).show();
        }

    }


}
