package com.example.jose.myapplication;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.widget.ListView;

import com.example.jose.myapplication.fragments.LoginFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
  public class ApplicationTest  extends ActionBarActivity {
    public ApplicationTest() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_list);
        if (savedInstanceState == null) {
            ListView yourListView = (ListView) findViewById(R.id.mi_list);

            ArrayList<Item> ai= new ArrayList<Item>();
            for(int m=1;m<=20;m++){
                Item i=new Item();
                i.setId(String.valueOf((int) Math.random() * 20));
                i.setCategory(String.valueOf((int)Math.random()*5));
                i.setDescription("Hola q ace");
                ai.add(i);
            }

// get data from the table by the ListAdapter
            ListAdapter customAdapter = new ListAdapter(this, R.layout.test_itemlistrows, ai);

            yourListView .setAdapter(customAdapter);

        }


    }
}