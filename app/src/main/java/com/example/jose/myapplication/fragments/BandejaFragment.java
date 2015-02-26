package com.example.jose.myapplication.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.adapters.PostAdapter;
import com.example.jose.myapplication.models.Post;

import java.util.ArrayList;

public class BandejaFragment extends Fragment {

    public BandejaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bandeja, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Post> postList = new ArrayList<Post>();

        Post  post1 = new Post();
        post1.setSolicita("Necesito sangre y plaquetas O+ positivo,por favor los que me puedan ayudarme " +
                        "porfavor pongase en contacto conmigo mi numero es 987645321"
        );
        ArrayList<String> rpta= new ArrayList<String>();
        rpta.add("Hola,Alan yo te ayudar ya mismo, ya te envie mis datos");
        Log.e("en el array",rpta.get(0));
        post1.setRespuesta(rpta);
        postList.add(post1);
        postList.add(post1);

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PostAdapter(postList, R.layout.card_posts));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




    }


}
