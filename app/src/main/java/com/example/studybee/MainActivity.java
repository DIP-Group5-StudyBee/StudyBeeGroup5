package com.example.studybee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loginprefs = getSharedPreferences("preference",MODE_PRIVATE);

        if(!loginprefs.contains("username")||loginprefs.getString("username","").isEmpty()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }


    }

    public void logOutClick(View view){
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

    public void JoinClicked(View view){
        Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
        startActivity(intent);
    }
}