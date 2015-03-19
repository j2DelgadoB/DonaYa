package com.example.jose.myapplication.adapters;

import android.app.Activity;
import android.os.AsyncTask;
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
         protected static TextView solicitud;
         protected  static ListView respuesta;
         protected  static EditText rpta_actual;
         protected  static Button enviar;

        public PostViewHolder(View v){
            super(v);
            id=(TextView) v.findViewById(R.id.idPostHidden);
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
        PostViewHolder.id.setText(po.getId());

        final String idPost=PostViewHolder.id.getText().toString();
        Log.d("textviewHidden:",idPost);

        PostViewHolder.solicitud.setText(po.getSolicita());
        rptaxpost = new ArrayList<String>();
        for (int j=0;j<po.getRespuesta().size();j++){
            if(po.getRespuesta().get(j).getResponde()!=null)
            rptaxpost.add(po.getRespuesta().get(j).getResponde());
        }
        final EditText rpta_temp = PostViewHolder.rpta_actual;

        final ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(actividad,R.layout.list_item_respuesta,R.id.list_item_respuesta_textview, rptaxpost);
        PostViewHolder.respuesta.setAdapter(adaptador);
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
                    AgregarRespuesta ar = new AgregarRespuesta(idPost, msj);
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
