package com.example.jose.myapplication.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.adapters.PostAdapter;
import com.example.jose.myapplication.models.Post;
import com.example.jose.myapplication.models.Respuesta;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BandejaFragment extends Fragment {
    JSONParser jParser = new JSONParser();
    JSONArray msjPost = null;
    ArrayList<Post> postList = new ArrayList<Post>();
    public BandejaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bandeja, container, false);
        //siempre ejecutando un hilo para que se muestre los mensajes de los posts actualizados de la base de datos
       mostrarPost post= new mostrarPost();
       post.execute();






        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    /*Pruebas
        ArrayList<Post> postList = new ArrayList<Post>();

        Post  post1 = new Post();
        post1.setSolicita("Necesito sangre y plaquetas O+ positivo,por favor los que me puedan ayudarme " +
                        "porfavor pongase en contacto conmigo mi numero es 987645321"
        );
        ArrayList<Respuesta> rpta= new ArrayList<Respuesta>();
        Respuesta responde = new Respuesta();
        responde.setResponde("Hola,Alan yo te ayudar ya mismo, ya te envie mis datos");
        rpta.add(responde);
        Log.e("en el array", rpta.get(0).getResponde());
        post1.setRespuesta(rpta);
        postList.add(post1);
        postList.add(post1);
        */
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PostAdapter(postList, R.layout.card_posts, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private class mostrarPost extends AsyncTask<Void,Void,String> {
        JSONObject json = null;

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("mostrar_todo","1"));
            try {
                //json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/mostrar_all_post.php","POST",par);
                json=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/mostrar_all_post.php","POST",par);
                Log.d("Mi json 1:", json.toString());
                int success = json.getInt("success");
                if (success==1){
                    Log.d("resultado:","correcto");


                    msjPost = json.getJSONArray("msjsPost");
                    Log.d("Log de msjPost", String.valueOf(msjPost.length()));
                    int next=0;
                    for (int i=0; i< msjPost.length();i++){
                        Log.d("for principal",String.valueOf(i));
                        Post  post = new Post();
                        ArrayList<Respuesta> arrayRpta= new ArrayList<Respuesta>();
                        Respuesta respuesta = new Respuesta();

                        JSONObject c = msjPost.getJSONObject(i);
                        String idPost= c.getString("idPost");
                        String idUser = c.getString("idUser");
                        String username = c.getString("username");
                        String msjSolicitud= c.getString("msjSolicitud");
                        post.setId(idPost);
                        post.setIdUser(idUser);
                        post.setUsername(username);
                        post.setSolicita(msjSolicitud);
                        String msjRespuesta = c.getString("msjRespuesta");
                        String idUserRpta=c.getString("idUserRpta");
                        String usernameRpta = c.getString("usernameRpta");

                        respuesta.setResponde(msjRespuesta);
                        respuesta.setIdUser(idUserRpta);
                        respuesta.setUsername(usernameRpta);
                        arrayRpta.add(respuesta);
                        Log.d("id:",idPost);
                        for (next=i+1;next<msjPost.length();next++) {
                            JSONObject d = msjPost.getJSONObject(next);
                            Log.d("id post next:",d.getString("idPost"));
                            if (idPost.equals(d.getString("idPost"))) {
                                String msjRespuestanext = d.getString("msjRespuesta");
                                String idUserRptanext=d.getString("idUserRpta");
                                String usernameRptanext = d.getString("usernameRpta");
                                //agregar las respuestas al arrayRpta
                                Respuesta respuestanext = new Respuesta();
                                respuestanext.setResponde(msjRespuestanext);
                                respuestanext.setIdUser(idUserRptanext);
                                respuestanext.setUsername(usernameRptanext);

                                arrayRpta.add(respuestanext);
                                Log.d("Respuestaconsecutiva:","si");
                            } else {
                                break;
                            }

                        }
                        Log.d("log array Rpta", String.valueOf(arrayRpta.size()));
                        post.setRespuesta(arrayRpta);
                        postList.add(post);
                        i=next-1;

                    }


                }//verificar su conexion a internet*/

            }catch (Exception e){
                e.printStackTrace();
            }


            ///OBTENGO EL JSON x ahora de todos los posts


            ///CREO VARIABLES DEL MODELO POST

            return null;
        }
        protected void onPostExecute(String file){
            /*Log de postList*/
            Log.d("Log de postList", String.valueOf(postList.size()));

            for (int i=0;i<postList.size();i++){
                Post p1= new Post();
                p1=postList.get(i);
                Log.d("Post "+p1.getId()+":",p1.getSolicita());
                for (int j=0;j<p1.getRespuesta().size();j++){
                    Log.d("Este es la respuesta numero "+j+":",p1.getRespuesta().get(j).getResponde());
                }
            }

            /* Log array list contact*/
            //ArrayList<String> contactlist= (ArrayList<String>) getArguments().getStringArrayList("IdAmigos").clone();
           // Log.d("Array en Bandeja",contactlist.get(0));
            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);

            recyclerView.setAdapter(new PostAdapter(postList, R.layout.card_posts, getActivity(),getActivity().getIntent().getStringExtra("MyID")));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            /*
            final EditText rpta_edit= (EditText) getActivity().findViewById(R.id.respuesta_editText);
            Log.d("este editText",rpta_edit.getText().toString()+" hola");
            rpta_edit.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        Toast.makeText(getActivity(), rpta_edit.getText() + " " + rpta_edit.getId(), Toast.LENGTH_SHORT).show();


                        return true;
                    }
                    return false;
                }
            });*/

        }


    }


}
