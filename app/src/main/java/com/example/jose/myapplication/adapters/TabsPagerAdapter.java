package com.example.jose.myapplication.adapters;

/**
 * Created by jose on 16/03/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jose.myapplication.fragments.Cita1Fragment;
import com.example.jose.myapplication.fragments.Cita2Fragment;
import com.example.jose.myapplication.fragments.Cita3Fragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Cita1Fragment();
            case 1:
                // Games fragment activity
                return new Cita2Fragment();
            case 2:
                // Movies fragment activity
                return new Cita3Fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}