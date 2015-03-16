package com.example.jose.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.jose.myapplication.R;
import com.example.jose.myapplication.adapters.TabsPagerAdapter;

/**
 * Created by jose on 16/03/2015.
 */
public class CitaFragment extends Fragment {


    //Mandatory Constructor
    public CitaFragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cita_tabs, container, false);
        return rootView;
    }




}
