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
import android.widget.Toast;


import com.example.jose.myapplication.LoginActivity;
import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.RegisterActivity;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.linkedin.LinkedInSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;

/**
 * Created by jose on 05/03/2015.
 */


public class LoginFragment extends android.support.v4.app.Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
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
    //android social network ASNE
    public static SocialNetworkManager mSocialNetworkManager;
    /**
     * SocialNetwork Ids in ASNE:
     * 1 - Twitter
     * 2 - LinkedIn
     * 3 - Google Plus
     * 4 - Facebook
     * 5 - Vkontakte
     * 6 - Odnoklassniki
     * 7 - Instagram
     */
    private Button facebook;
    private Button twitter;
    private Button linkedin;
    private Button googleplus;

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
        Button btnRegistro = (Button) rootView.findViewById(R.id.btnRegistrarse);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });
        //ASNE
        ((LoginActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        // init buttons and set Listener
        facebook = (Button) rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        twitter = (Button) rootView.findViewById(R.id.twitter);
        twitter.setOnClickListener(loginClick);
        linkedin = (Button) rootView.findViewById(R.id.linkedin);
        linkedin.setOnClickListener(loginClick);
        googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        //Get Keys for initiate SocialNetworks
        String TWITTER_CONSUMER_KEY = getActivity().getString(R.string.twitter_consumer_key);
        String TWITTER_CONSUMER_SECRET = getActivity().getString(R.string.twitter_consumer_secret);
        String TWITTER_CALLBACK_URL = "oauth://ASNE";
        String LINKEDIN_CONSUMER_KEY = getActivity().getString(R.string.linkedin_consumer_key);
        String LINKEDIN_CONSUMER_SECRET = getActivity().getString(R.string.linkedin_consumer_secret);
        String LINKEDIN_CALLBACK_URL = "https://asneTutorial";

        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));
        String linkedInScope = "r_basicprofile+r_fullprofile+rw_nus+r_network+w_messages+r_emailaddress+r_contactinfo";

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(LoginActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager FacebookSocialNetwork
            FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
            mSocialNetworkManager.addSocialNetwork(fbNetwork);

            //Init and add to manager TwitterSocialNetwork
            TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_CALLBACK_URL);
            mSocialNetworkManager.addSocialNetwork(twNetwork);

            //Init and add to manager LinkedInSocialNetwork
            LinkedInSocialNetwork liNetwork = new LinkedInSocialNetwork(this, LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET, LINKEDIN_CALLBACK_URL, linkedInScope);
            mSocialNetworkManager.addSocialNetwork(liNetwork);

            //Init and add to manager LinkedInSocialNetwork
            GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
            mSocialNetworkManager.addSocialNetwork(gpNetwork);

            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, LoginActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }

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
                //json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/login.php","POST",par);
                  json=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/login.php","POST",par);

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
                        editor.putString("id",ID);
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
  //Social ASNE
  private void initSocialNetwork(SocialNetwork socialNetwork){
      if(socialNetwork.isConnected()){
          switch (socialNetwork.getID()){
              case FacebookSocialNetwork.ID:
                  facebook.setText("Show Facebook profile");
                  break;
              case TwitterSocialNetwork.ID:
                  twitter.setText("Show Twitter profile");
                  break;
              case LinkedInSocialNetwork.ID:
                  linkedin.setText("Show LinkedIn profile");
                  break;
              case GooglePlusSocialNetwork.ID:
                  googleplus.setText("Show GooglePlus profile");
                  break;
          }
      }
  }
    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    //Login listener

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
                case R.id.twitter:
                    networkId = TwitterSocialNetwork.ID;
                    break;
                case R.id.linkedin:
                    networkId = LinkedInSocialNetwork.ID;
                    break;
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;
            }
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if(!socialNetwork.isConnected()) {
                if(networkId != 0) {
                    socialNetwork.requestLogin();
                    LoginActivity.showProgress("Loading social person");
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {
                startProfile(socialNetwork.getID());
            }
        }
    };

    @Override
    public void onLoginSuccess(int networkId) {
        LoginActivity.hideProgress();
        startProfile(networkId);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        LoginActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private void startProfile(int networkId){
      ProfileFragment profile = ProfileFragment.newInstannce(networkId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("profile")
                .replace(R.id.container, profile)
                .commit();
    }

}
