package com.example.jose.myapplication.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose.myapplication.LoginActivity;
import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.adapters.RecyclerHolderHeaderAdapter;
import com.example.jose.myapplication.models.HeaderItem;
import com.example.jose.myapplication.models.Perfil;
import com.example.jose.myapplication.models.SocialData;
import com.example.jose.myapplication.utils.JSONParser;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.linkedin.LinkedInSocialNetwork;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements OnRequestSocialPersonCompleteListener {
    private String message = "Need simple social networks integration? Check this lbrary:";
    private String link = "https://github.com/gorbin/ASNE";

    private static final String NETWORK_ID = "NETWORK_ID";
    private SocialNetwork socialNetwork;
    private int networkId;
    private ImageView photo;
    private TextView name;
    private TextView id;
    private TextView info;
    private Button registrar;
    private Button continuar;
    private RelativeLayout frame;
    private String socialPersonString;
    private SocialData socialData;


    public static ProfileFragment newInstannce(int id) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(NETWORK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        networkId = getArguments().containsKey(NETWORK_ID) ? getArguments().getInt(NETWORK_ID) : 0;
        ((LoginActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        View rootView = inflater.inflate(R.layout.profile_social_fragment, container, false);

        frame = (RelativeLayout) rootView.findViewById(R.id.frame);
        photo = (ImageView) rootView.findViewById(R.id.imageView);
        name = (TextView) rootView.findViewById(R.id.name);
        id = (TextView) rootView.findViewById(R.id.id);
        info = (TextView) rootView.findViewById(R.id.info);
        registrar = (Button) rootView.findViewById(R.id.friends);
        registrar.setOnClickListener(registrarClick);
        continuar = (Button) rootView.findViewById(R.id.share);
        continuar.setOnClickListener(continuarClick);
        colorProfile(networkId);

        socialNetwork = LoginFragment.mSocialNetworkManager.getSocialNetwork(networkId);
        socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
        socialNetwork.requestCurrentPerson();

        LoginActivity.showProgress("Loading social person");
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_social, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                socialNetwork.logout();
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
        LoginActivity.hideProgress();
        name.setText(socialPerson.name);
        id.setText(socialPerson.id);
        socialPersonString = socialPerson.toString();
        String infoString = socialPersonString.substring(socialPersonString.indexOf("{")+1, socialPersonString.lastIndexOf("}"));
        info.setText(infoString.replace(", ", "\n"));
        Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);

        socialData = new SocialData();
        socialData.setId(socialPerson.id);
        socialData.setName(socialPerson.name);
        socialData.setAvatarURL(socialPerson.avatarURL);
        socialData.setProfileURL(socialPerson.profileURL);
        socialData.setEmail(socialPerson.email);


    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        LoginActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener registrarClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           /* FriendsFragment friends = FriendsFragment.newInstannce(networkId);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("friends")
                    .replace(R.id.container, friends)
                    .commit();*/
        }
    };

    private View.OnClickListener continuarClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            crearUserSocial userSocial = new crearUserSocial(socialData);
            userSocial.execute();

          /*  AlertDialog.Builder ad = alertDialogInit("Would you like to post Link:", link);
            ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Bundle postParams = new Bundle();
                    postParams.putString(SocialNetwork.BUNDLE_NAME, "Simple and easy way to add social networks for android application");
                    postParams.putString(SocialNetwork.BUNDLE_LINK, link);
                    if(networkId == GooglePlusSocialNetwork.ID) {
                        socialNetwork.requestPostDialog(postParams, postingComplete);
                    } else {
                        socialNetwork.requestPostLink(postParams, message, postingComplete);
                    }
                }
            });
            ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
            ad.create().show();*/
        }
    };

    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            Toast.makeText(getActivity(), "Error while sending: " + errorMessage, Toast.LENGTH_LONG).show();
        }
    };

    private void colorProfile(int networkId){
        int color = getResources().getColor(R.color.dark);
        int image = R.drawable.user;
        switch (networkId) {
            case TwitterSocialNetwork.ID:
                color = getResources().getColor(R.color.twitter);
                image = R.drawable.twitter_user;
                break;
            case LinkedInSocialNetwork.ID:
                color = getResources().getColor(R.color.linkedin);
                image = R.drawable.linkedin_user;
                break;
            case GooglePlusSocialNetwork.ID:
                color = getResources().getColor(R.color.googleplus);
                image = R.drawable.linkedin_user;
                break;
            case FacebookSocialNetwork.ID:
                color = getResources().getColor(R.color.facebook);
                image = R.drawable.com_facebook_profile_picture_blank_square;
                break;
        }
        frame.setBackgroundColor(color);
        name.setTextColor(color);
        registrar.setBackgroundColor(color);
        continuar.setBackgroundColor(color);
        photo.setBackgroundColor(color);
        photo.setImageResource(image);
    }

    private AlertDialog.Builder alertDialogInit(String title, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setCancelable(true);
        return ad;
    }
    private class crearUserSocial extends AsyncTask<Void,Void,String> {
        JSONObject json=null;
        JSONParser jParser = new JSONParser();
        SocialData socialData= new SocialData();
        public crearUserSocial(SocialData socialData) {
            this.socialData= socialData;
        }

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            Log.i("captura", socialData.toString());
            String username= networkId+cuentaSocial(networkId).substring(0,1).concat(socialData.getId());
            String password= new StringBuilder(username).reverse().toString();
            String urlCuentaSocial;
            if (networkId==1) { //TWitter{
                urlCuentaSocial="https://twitter.com/intent/user?user_id=".concat(socialData.getId());
            } else {
                urlCuentaSocial=socialData.getProfileURL();
            }

            Log.d("username",username);
            Log.d("password",password);
            par.add(new BasicNameValuePair("username",username));
            par.add(new BasicNameValuePair("password",password));
            par.add(new BasicNameValuePair("email",socialData.getEmail()));

            par.add(new BasicNameValuePair("tipoCuenta",cuentaSocial(networkId)));

            par.add(new BasicNameValuePair("name",socialData.getName()));
            par.add(new BasicNameValuePair("urlCuentaSocial",urlCuentaSocial));
            par.add(new BasicNameValuePair("foto",socialData.getAvatarURL()));
            //urlCuentaSocial

            try {
                //  json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/registro_perfil.php","GET",par);
                  json=jParser.makeHttpRequest("http://104.131.187.32/SoyDonante/registro_user_perfil.php","POST",par);
                //Log.d("Mi json:", json.toString());
                int success = json.getInt("success");
                if (success==1){
                    Log.d("","se guardo en la base de datos correctamente");

                    Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                    intent.putExtra("MyID",json.getString("id"));
                    intent.putExtra("MyUsername",username);
                    intent.putExtra("MyEmail",socialData.getEmail());
                    intent.putExtra("Photo",socialData.getAvatarURL());

                    startActivity(intent);
                }//verificar su conexion a internet

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file){


        }
    }
    /**
     * SocialNetwork Ids in ASNE:
     * 1 - Twitter
     * 2 - LinkedIn
     * 3 - Google Plus
     * 4 - Facebook
     */
    private String cuentaSocial(int networkId){
        if (networkId==1){
            return "twitter";
        } else if (networkId==3){
            return "google";
        }  else if (networkId==4){
            return "facebook";
        }
        return "otro";
    }


}
