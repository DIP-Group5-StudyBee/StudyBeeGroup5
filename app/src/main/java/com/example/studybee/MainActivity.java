package com.example.studybee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joinBtn = (Button)findViewById(R.id.joinButton);
        Button hostBtn = (Button)findViewById(R.id.createButton);

        bottomNavigationView=findViewById(R.id.bottomNav);


        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        SharedPreferences loginprefs = getSharedPreferences("preference", MODE_PRIVATE);

        if (!loginprefs.contains("username") || loginprefs.getString("username", "").isEmpty()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

    }

    public void joinClicked(View v){
        startActivity(new Intent(MainActivity.this, JoinActivity.class));
    }

    public void createClicked(View v){
        startActivity(new Intent(MainActivity.this, HostActivity.class));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment frag = null;

                    switch (menuItem.getItemId())
                    {
                        case R.id.home:
                            frag = new HomeFragment();
                            break;

                        case R.id.quickjoin:
                            frag = new QuickjoinFragment();
                            break;

                        case R.id.profile:
                            frag = new ProfileFragment();
//                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

                    return true;
                }
            };

    public void logOutClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // This call is missing.

        Log.d(TAG, sharedPreferences.getString("username", ""));
        Log.d(TAG, sharedPreferences.getString("password", ""));
    }

}