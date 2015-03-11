package com.example.jose.myapplication;

/**
 * Created by jose on 11/03/2015.
 */
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;

import com.example.jose.myapplication.fragments.LoginFragment;


public class LoginActivity extends ActionBarActivity {
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
            // user = (EditText)findViewById(R.id.editText);
            //   pass = (EditText)findViewById(R.id.editText2);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            LoginFragment fragment = new LoginFragment();
            fragmentTransaction.add(R.id.container, fragment).hide(fragment);
            fragmentTransaction.show(fragment).commit();

        }


    }
}