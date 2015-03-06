package com.example.jose.myapplication.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.view.LayoutInflater;

import com.example.jose.myapplication.MainActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.fragments.BandejaFragment;
import com.example.jose.myapplication.models.Post;

import java.util.ArrayList;


/**
 * Created by jose on 26/02/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    ArrayList<Post> postList;
    private int itemLayout;
    private Activity actividad;


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
         protected static TextView solicitud;
         protected  static ListView respuesta;

        public PostViewHolder(View v){
            super(v);
            solicitud = (TextView) v.findViewById(R.id.textSolicitud);
            respuesta = (ListView) v.findViewById(R.id.seccion_respuestas);

        }
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        Post po= postList.get(i);
        PostViewHolder.solicitud.setText(po.getSolicita());
        ArrayList<String> rptaxpost = new ArrayList<String>();
        for (int j=0;j<po.getRespuesta().size();j++){
            rptaxpost.add(po.getRespuesta().get(j).getResponde());
        }

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(actividad,R.layout.list_item_respuesta,R.id.list_item_respuesta_textview, rptaxpost);
        PostViewHolder.respuesta.setAdapter(adaptador);

        //  PostViewHolder.respuesta.setText( po.getRespuesta().get(0).getResponde());//con for getRespuesta().size crear text views de respuesta maximo 2 y opcion para ampliar
       // Log.e("en el adapter", po.getRespuesta().get(0).getResponde());
    }
}
