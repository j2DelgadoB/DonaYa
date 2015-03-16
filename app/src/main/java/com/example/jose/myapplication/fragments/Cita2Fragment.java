package com.example.jose.myapplication.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;

/**
 * Created by jose on 13/03/2015.
 */
public class Cita2Fragment extends Fragment {

    public Cita2Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_cita2, container, false);
        Button btnNextCita2=(Button) rootView.findViewById(R.id.btnNextCita2);
        btnNextCita2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tratar de pasar por intent para que position displayview sea 6
                //displayView(6);
            }
        });
         return rootView;
    }
}
