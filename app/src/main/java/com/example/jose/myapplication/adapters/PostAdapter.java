package com.example.jose.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.view.LayoutInflater;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.models.Post;

import java.util.ArrayList;


/**
 * Created by jose on 26/02/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    ArrayList<Post> postList;
    private int itemLayout;

    public PostAdapter(ArrayList<Post> postList){
        this.postList=postList;

    }

    public PostAdapter(ArrayList<Post> postList, int item_layout) {
        this.postList=postList;
        this.itemLayout=item_layout;
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
         protected  static TextView respuesta;

        public PostViewHolder(View v){
            super(v);
            solicitud = (TextView) v.findViewById(R.id.textSolicitud);
            respuesta = (TextView) v.findViewById(R.id.textRpta);
        }
    }

    @Override
    public void onBindViewHolder(PostViewHolder postViewHolder, int i) {
        Post po= postList.get(i);
        PostViewHolder.solicitud.setText(po.getSolicita());
        PostViewHolder.respuesta.setText( po.getRespuesta().get(0).getResponde());//con for getRespuesta().size crear text views de respuesta maximo 2 y opcion para ampliar
        Log.e("en el adapter", po.getRespuesta().get(0).getResponde());
    }
}
