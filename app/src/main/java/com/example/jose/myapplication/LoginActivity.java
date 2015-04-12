package com.example.jose.myapplication;

/**
 * Created by jose on 11/03/2015.
 */
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;
import com.example.jose.myapplication.fragments.LoginFragment;


 public class LoginActivity extends ActionBarActivity implements android.support.v4.app.FragmentManager.OnBackStackChangedListener{
     public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
     private static ProgressDialog pd;
     static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        homeAsUpByBackStack();
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();
            // user = (EditText)findViewById(R.id.editText);
            //   pass = (EditText)findViewById(R.id.editText2);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
            LoginFragment fragment = new LoginFragment();
            fragmentTransaction.add(R.id.container, fragment).hide(fragment);
            fragmentTransaction.show(fragment).commit();
            /*
            *  getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
            * */

        }


    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         return true;
     }

     @Override
     public void onBackStackChanged() {
         homeAsUpByBackStack();
     }

     private void homeAsUpByBackStack() {
         int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
         if (backStackEntryCount > 0) {
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         } else {
             getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         }
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case android.R.id.home:
                 getSupportFragmentManager().popBackStack();
                 return true;
         }
         return super.onOptionsItemSelected(item);
     }

     public static void showProgress(String message) {
         pd = new ProgressDialog(context);
         pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         pd.setMessage(message);
         pd.setCancelable(false);
         pd.setCanceledOnTouchOutside(false);
         pd.show();
     }

     public static void hideProgress() {
         pd.dismiss();
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
         if (fragment != null) {
             fragment.onActivityResult(requestCode, resultCode, data);
         }
     }
 }