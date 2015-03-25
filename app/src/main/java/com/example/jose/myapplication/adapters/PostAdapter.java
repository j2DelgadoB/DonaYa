package com.example.jose.myapplication.adapters;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.models.Post;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jose on 26/02/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    ArrayList<Post> postList;
    private int itemLayout;
    private Activity actividad;
    private String my_id;

    ArrayList<String> rptaxpost;
    JSONParser jParser = new JSONParser();



    public PostAdapter(ArrayList<Post> postList){
        this.postList=postList;

    }

    public PostAdapter(ArrayList<Post> postList, int item_layout) {
        this.postList=postList;
        this.itemLayout=item_layout;
    }

    public PostAdapter(ArrayList<Post> postList, int item_layout, Activity a) {
        this.postList=postList;
        this.itemLayout=item_layout;
        this.actividad=a;
    }

    public PostAdapter(ArrayList<Post> postList, int item_layout, Activity a, String ar) {
        this.postList=postList;
        this.itemLayout=item_layout;
        this.actividad=a;
        this.my_id=ar;
    }



    @Override
    public int getItemCount() {
        return postList.size();
    }
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from
                (viewGroup.getContext()).inflate
                (itemLayout,viewGroup,false);

        return new PostViewHolder(itemView);
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
         protected static TextView id;
         protected static TextView idUserPost;
         protected static TextView username;
         protected static Button addContact;
         protected static TextView solicitud;
         protected  static ListView respuesta;
         protected  static EditText rpta_actual;
         protected  static Button enviar;

        public PostViewHolder(View v){
            super(v);
            id=(TextView) v.findViewById(R.id.idPostHidden);
            idUserPost=(TextView) v.findViewById(R.id.idUserPost);
            username =(TextView) v.findViewById(R.id.etNombreSolicitante);
            addContact= (Button) v.findViewById(R.id.btnAddContactSolicitante);
            solicitud = (TextView) v.findViewById(R.id.textSolicitud);
            respuesta = (ListView) v.findViewById(R.id.seccion_respuestas);
            rpta_actual=(EditText) v.findViewById(R.id.respuesta_editText);
            enviar = (Button) v.findViewById(R.id.btnEnviarRpta);
           // Log.d("Estoy en el card:", ((Integer) v.getTag()).toString());
        }
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder, int i) {
        Post po= postList.get(i);
        Log.d("en la lista",po.getId());
        PostViewHolder.id.setText(my_id);

        final String my_id=PostViewHolder.id.getText().toString();
        Log.d("textviewHidden:",my_id);

        PostViewHolder.idUserPost.setText(po.getIdUser());
        final TextView user_post_temp = PostViewHolder.idUserPost;//supuesto amigo si no es uno mismo --luego condicional
        Log.d("mi posible amigo:",user_post_temp.getText().toString());
        PostViewHolder.username.setText(po.getUsername());
        PostViewHolder.solicitud.setText(po.getSolicita());
        rptaxpost = new ArrayList<String>();
        for (int j=0;j<po.getRespuesta().size();j++){
            Log.d("tamaño de respuesta:",String.valueOf(po.getRespuesta().size()));
            if(po.getRespuesta().get(j).getResponde()!=null)
            rptaxpost.add(po.getRespuesta().get(j).getResponde());//ERROR
        }

        final EditText rpta_temp = PostViewHolder.rpta_actual;

        final ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(actividad,R.layout.list_item_respuesta,R.id.list_item_respuesta_textview, rptaxpost);
        PostViewHolder.respuesta.setAdapter(adaptador);
        PostViewHolder.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String id_amigo=user_post_temp.getText().toString();
                if(my_id.equals(id_amigo)){

                    Log.d("NO SE PUEDE:","enviar solicitud a si mismo");
                }else{ //otra condicional o hilo para saber si lo tiene ya agregado en vez de ejecutar otro hilo q le pase un arraylist de contacts de la actividad principal
                    EnviarSolicitudContacto esc= new EnviarSolicitudContacto(my_id,id_amigo);
                    esc.execute();
                }
            }
        });
        PostViewHolder.enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Perform action on key press
                Toast.makeText(actividad, rpta_temp.getText().toString() + " " + String.valueOf(rpta_temp.getId()), Toast.LENGTH_LONG).show();
                if (rpta_temp.getText().toString().equals("") || rpta_temp.getText().toString().equals(null)){
                    Toast.makeText(actividad,"Escriba su respuesta",Toast.LENGTH_SHORT);
                }else {
                    adaptador.add(rpta_temp.getText().toString());
                    //Log.d("elemt agregado",rptaxpost.get(rptaxpost.size()-1).toString());
                    // Log.d("adaptador add rpta:",adaptador.getItem(0).toString());
                    adaptador.notifyDataSetChanged();
                    Log.d("Log:", "todas las respuestas + añadidas");
                    for (int i = 0; i < adaptador.getCount(); i++) {
                        Log.d("rptas:", adaptador.getItem(i));
                    }


                    String msj = adaptador.getItem(adaptador.getCount() - 1);
                    AgregarRespuesta ar = new AgregarRespuesta(my_id, msj);
                    ar.execute();
                }
            }


        });
        /*
        rpta_temp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    rpta_temp.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            // If the event is a key-down event on the "enter" button

                            return false;
                        }
                    });
                }

            }
        }); */

        //  PostViewHolder.respuesta.setText( po.getRespuesta().get(0).getResponde());//con for getRespuesta().size crear text views de respuesta maximo 2 y opcion para ampliar
       // Log.e("en el adapter", po.getRespuesta().get(0).getResponde());
    }

    private  class EnviarSolicitudContacto extends  AsyncTask<Void,Void,String> {
        JSONObject json = null;
        String mi_id,id_amigo;
        EnviarSolicitudContacto(String mi_id,String id_amigo){
            this.mi_id=mi_id;
            this.id_amigo=id_amigo;
        }

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("idUser",mi_id));

            par.add(new BasicNameValuePair("idAmigo", id_amigo));
            par.add(new BasicNameValuePair("solicitud","enviada"));
            try {
                jParser= new JSONParser();
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/solicitud_add_contacto.php","GET",par);
                Log.d("Mi json:", json.toString());
                int success = json.getInt("success");
                if (success==1){
                    Log.d("","se guardo la respuesta agregada en la base de datos correctamente");
                    Log.d("mi id2",mi_id);
                    Log.d("id amigo2",id_amigo);
                }//verificar su conexion a internet*/

            }catch (Exception e){
                Log.e("error",e.toString());
            }
            return null;
        }
    }

    private class AgregarRespuesta extends AsyncTask<Void,Void,String>{
        JSONObject json=null;
        String idPost,newRpta;
        AgregarRespuesta(String idPost, String newRpta){
            this.idPost=idPost;
            this.newRpta=newRpta;
        }
        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            par.add(new BasicNameValuePair("idPost",idPost));
            par.add(new BasicNameValuePair("newRpta",newRpta));
            Log.d("pasando por parametros:",idPost+" "+newRpta);
            try {
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/add_rpta.php","POST",par);

                int success = json.getInt("success");
                if (success==1){
                    Log.d("Mi json en postAdapter:", json.toString());
                    Log.d("","se guardo la respuesta agregada en la base de datos correctamente");
                }//verificar su conexion a internet*/

            }catch (Exception e){
                Log.e("error",e.toString());
            }
        return null;
        }
    }
}
