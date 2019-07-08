package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.strsar.mapsrun.R;

public class MainActivity extends AppCompatActivity  {

    LocationManager locationManager;
    String mprovider;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    int i=1;
    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        String uname = sharedPreferences.getString("devli_name", null);


        if(sharedPreferences!=null) {
            if (uname != null || uname == "") {

            } else {
                Intent it = new Intent(MainActivity.this, Login.class);
                startActivity(it);
            }
        }

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new SearchFragment();
        transaction.replace(R.id.main_container, fragment).commit();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id){
                    case R.id.action_search:
                        intent=new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        fragment = new SearchFragment();
                        break;
                  /*  case R.id.action_cart:
                        intent=new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        fragment = new CartFragment();
                        break;
                  */  case R.id.action_hot_deals:
                        intent=new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        fragment = new DealsFragment();
                        break;
                    case R.id.action_small_deals:
                       intent=new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        fragment = new Orders();
                        break;
                    case R.id.profile:
                        fragment =new Profile();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(bottomNavigation.getSelectedItemId()==R.id.action_search)
        {
            super.onBackPressed();
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);


        }
        else{
            bottomNavigation.setSelectedItemId(R.id.action_search);
        }

    }
}
