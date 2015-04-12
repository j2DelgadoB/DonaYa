package com.example.jose.myapplication;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.jose.myapplication.fragments.RegisterFragment;

/**
 * Created by jose on 21/03/2015.
 */
public class RegisterActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
            // user = (EditText)findViewById(R.id.editText);
            //   pass = (EditText)findViewById(R.id.editText2);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            RegisterFragment fragment = new RegisterFragment();
            fragmentTransaction.add(R.id.containerRegister, fragment).hide(fragment);
            fragmentTransaction.show(fragment).commit();

        }


    }
}
