package com.example.jose.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;

/**
 * Created by jose on 13/03/2015.
 */
public class Cita1Fragment extends Fragment {
    public Cita1Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cita1, container, false);
        Button btnNoCita=(Button) rootView.findViewById(R.id.btnNoCita1);
        btnNoCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
         return rootView;
    }
}
