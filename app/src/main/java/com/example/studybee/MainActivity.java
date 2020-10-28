package com.example.studybee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studybee.adapter.UpcomingMeetingAdapter;
import com.example.studybee.model.UpcomingMeeting;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    String isTA;
    String msgType;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;

    RecyclerView upcomingmRecycler;
    UpcomingMeetingAdapter upcomingMeetingAdapter;

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

        //set up dummy data for model class for upcoming meetings
        List<UpcomingMeeting> upcomingMeetingList = new ArrayList<>();

        upcomingMeetingList.add(new UpcomingMeeting("LSY", "Java Programming", "Join us to code for fun!"));
        upcomingMeetingList.add(new UpcomingMeeting("KSC", "Engine Comm", "Help with presentation pls"));
        upcomingMeetingList.add(new UpcomingMeeting("ABC", "Alphabets", "Let's learn together"));

        setUpcomingmRecycler(upcomingMeetingList);

        SharedPreferences loginprefs = getSharedPreferences("preference", MODE_PRIVATE);

        if (!loginprefs.contains("username") || loginprefs.getString("username", "").isEmpty()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

    }

    private void setUpcomingmRecycler(List<UpcomingMeeting> upcomingMeetingList){
        upcomingmRecycler = findViewById(R.id.upcomingmeeting_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        upcomingmRecycler.setLayoutManager(layoutManager);
        upcomingMeetingAdapter = new UpcomingMeetingAdapter(this, upcomingMeetingList);
        upcomingmRecycler.setAdapter(upcomingMeetingAdapter);
    }

    public void joinClicked(View v){
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        isTA = sh.getString("identity","");
        if (isTA.equals("Teaching Assistant")) {
            saveAsPreferences();
            //Toast.makeText(getApplicationContext(),"Hello lobbyact", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LobbyActivity.class));
        } else {
            //Toast.makeText(getApplicationContext(),"Hello FAIL", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, JoinActivity.class));
        }

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

    public void saveAsPreferences(){
        //HOW TO ADD WILDCARD TO SELECTION???!!!!
        SharedPreferences prefs = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msgType","%");
        editor.putString("groupSize","%");
        editor.putString("studyStyle","%");
        editor.putString("teachingAssistant","Yes");
        editor.putString("course","%");
        editor.commit();
    }

}
