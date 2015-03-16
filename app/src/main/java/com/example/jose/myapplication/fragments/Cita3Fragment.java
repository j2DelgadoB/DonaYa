package com.example.jose.myapplication.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jose.myapplication.R;

/**
 * Created by jose on 13/03/2015.
 */
public class Cita3Fragment extends Fragment {
    public Cita3Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_cita3, container, false);
         return rootView;
    }
}
