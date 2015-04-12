package com.example.jose.myapplication.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jose.myapplication.LoginActivity;
import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 21/03/2015.
 */
public class RegisterFragment extends android.support.v4.app.Fragment {
    JSONParser jParser = new JSONParser();
    public RegisterFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        Button btn = (Button) rootView.findViewById(R.id.buttonRegistro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register registro= new Register();
                registro.execute();
            }
        });
        return rootView;
    }

    private class Register extends AsyncTask<Void,Void,String> {
        JSONObject json = null;


        @Override
        protected String doInBackground(Void... params) {
            EditText username=(EditText)getActivity().findViewById(R.id.usernameRegistro);
            EditText pass=(EditText)getActivity().findViewById(R.id.contraRegistro);
            EditText email=(EditText)getActivity().findViewById(R.id.emailRegistro);

            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("username",username.getText().toString()));
            par.add(new BasicNameValuePair("password",pass.getText().toString()));
            par.add(new BasicNameValuePair("email",email.getText().toString()));
            try {
                //json = jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/registro_perfil.php", "GET", par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/registro_perfil.php","GET",par);

                Log.d("mi json ", json.toString());
                int success = json.getInt("success");
                if (success == 1) {
                    Log.d("usuario creado","");
                }

            }catch (Exception e){}
            return null;
        }
        protected  void onPostExecute(String file_url) {
            // Toast.makeText(getActivity(),"mi json " ,Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        }
    }
}
