package com.example.jose.myapplication.adapters;


import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.fragments.PerfilFragment;
import com.example.jose.myapplication.models.HeaderItem;
import com.example.jose.myapplication.utils.AppController;
import com.example.jose.myapplication.utils.BitmapTransform;
import com.squareup.picasso.Picasso;

import net.i2p.android.ext.floatingactionbutton.AddFloatingActionButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by jose on 29/03/2015.
 */

 public class RecyclerHolderHeaderAdapter extends RecyclerView.Adapter<RecyclerHolderHeaderAdapter.RecyclerViewHolder> {
    ArrayList<HeaderItem> list_header = new ArrayList<HeaderItem>();
    ArrayList<HeaderItem> list_header_fondo = new ArrayList<HeaderItem>();
    Bitmap image=null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    //ImageLoader imageLoader2 = AppController.getInstance().getImageLoader();
    Activity activity;
    private int itemLayout;
    private int buttonLoaderVisibility=0;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    public RecyclerHolderHeaderAdapter(ArrayList<HeaderItem> list_header, int itemLayout){

        this.list_header=list_header;
        this.itemLayout=itemLayout;
    }
    public RecyclerHolderHeaderAdapter(ArrayList<HeaderItem> list_header, int itemLayout, Activity activity){
        this.list_header=list_header;
        this.itemLayout=itemLayout;
        this.activity = activity;
    }
    public RecyclerHolderHeaderAdapter(int buttonLoaderVisibility,ArrayList<HeaderItem> list_header, int itemLayout, Activity activity){
        this.list_header=list_header;
        this.itemLayout=itemLayout;
        this.activity = activity;
        this.buttonLoaderVisibility=buttonLoaderVisibility;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder recyclerViewHolder, int i) {
        //accion
        if (buttonLoaderVisibility!=0)
            setButtonsLoadEnable();
        else
            setButtonsLoadDisable();

       // RecyclerViewHolder.webTitulo.setText(list_web.get(i).getWebTitulo());
       // pintarImagen pI= new pintarImagen(i);
       // pI.execute();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        //if (imageLoader2 == null)
        //    imageLoader2 = AppController.getInstance().getImageLoader();
        //Log.d("urlFoto",list_header.get(i).getUrlFoto());
        RecyclerViewHolder.webImagen.setImageUrl(list_header.get(i).getUrlFoto(), imageLoader);
        RecyclerViewHolder.webImagenFondo.setImageUrl(list_header.get(i).getUrlFondo(),imageLoader);
        Log.d("url fondo",list_header.get(i).getUrlFondo());
        //Picasso.with(activity).load(list_header_fondo.get(i).getUrlFoto()).into(RecyclerViewHolder.webImagenFondo);
        RecyclerViewHolder.webImagen.setDefaultImageResId(R.drawable.user);
        RecyclerViewHolder.webImagenFondo.setDefaultImageResId(R.drawable.header_bg);

        RecyclerViewHolder.webImagen.setErrorImageResId(R.drawable.user);
        RecyclerViewHolder.webImagenFondo.setErrorImageResId(R.drawable.header_bg);

    /*    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

// Loads given image
        Picasso.with(activity)
                .load(list_header_fondo.get(i).getUrlFoto())
                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .skipMemoryCache()
                .resize(size, size)
                .centerInside()
                .into(RecyclerViewHolder.webImagenFondo);*/
    }

    @Override
    public int getItemCount() {
        return list_header.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        protected static NetworkImageView webImagen;
        protected static NetworkImageView webImagenFondo;
       // protected static TextView webTitulo;
        protected static AddFloatingActionButton buttonLoadFoto;
        protected static Button buttonLoadFondo;
        protected static Button upload;
        public RecyclerViewHolder(View v) {
            super(v);
            webImagen=(NetworkImageView) v.findViewById(R.id.imageFoto);
            webImagenFondo=(NetworkImageView) v.findViewById(R.id.imageFondo);
            //webTitulo=(TextView) v.findViewById(R.id.txt);
            buttonLoadFoto=(AddFloatingActionButton) v.findViewById(R.id.buttonLoadPicture);
            buttonLoadFondo=(Button)v.findViewById(R.id.buttonLoadPicture2);
            upload=(Button)v.findViewById(R.id.upload);
        }
    }

    public void setButtonsLoadDisable(){


        RecyclerViewHolder.upload.setVisibility(View.GONE);


        RecyclerViewHolder.buttonLoadFoto.setVisibility(View.GONE);


        RecyclerViewHolder.buttonLoadFondo.setVisibility(View.GONE);


    }

    public void setButtonsLoadEnable(){


        RecyclerViewHolder.upload.setVisibility(View.VISIBLE);


        RecyclerViewHolder.buttonLoadFoto.setVisibility(View.VISIBLE);


        RecyclerViewHolder.buttonLoadFondo.setVisibility(View.VISIBLE);


    }

 }
