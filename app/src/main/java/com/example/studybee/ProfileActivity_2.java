package com.example.studybee;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProfileActivity_2 extends AppCompatActivity {
    TextView dis_firstname;
    TextView dis_gender;
    TextView dis_faculty;
    TextView dis_username;
    TextView dis_age;
    TextView dis_email;
    Button record_Btn;
    Button info_Btn;
    ImageButton edit_Btn;


    String username;
    String firstname;
    String faculty;
    String gender;
    String age;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        dis_firstname=(TextView) findViewById(R.id.full_name);
        dis_username=(TextView) findViewById(R.id.profile_name);
        dis_gender=(TextView) findViewById(R.id.gender);
        dis_faculty=(TextView) findViewById(R.id.school);
        dis_age =(TextView) findViewById(R.id.age);


        record_Btn=(Button) findViewById(R.id.record_button);
        info_Btn=(Button) findViewById(R.id.info_button);
        edit_Btn =(ImageButton) findViewById(R.id.edit_button);

        //on create display the information from the database using the retrieval from PHP
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        username = sh.getString("username", "");
        dis_username.setText(username);
        firstname = sh.getString("firstname", "");
        dis_firstname.setText(firstname);
        gender = sh.getString("gender", "");
        dis_gender.setText(gender);
        faculty = sh.getString("faculty", "");
        dis_faculty.setText(faculty);
        age = sh.getString("age", "");
        dis_age.setText(age);

        edit_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editClicked();
            }
        });
    }

    //For all the buttons
    public void editClicked(){
        startActivity(new Intent(ProfileActivity_2.this, ProfileEditActivity.class));
    }

    //for bottom navigation bar
//    public void homeBotClicked(View view){
//        startActivity(new Intent(ProfileActivity_2.this, MainActivity.class));
//
//    }
//    public void profileBotClicked(View view){
//        startActivity(new Intent(ProfileActivity_2.this, ProfileActivity_2.class));
//
//    }
    //    public void joinBotClicked(View view){
    //        startActivity(new Intent(ProfileActivity_2.this, ProfileEditActivity.class));
    //
    //    }
    //Middle navigation tab

    public void infotabClicked(View view){
        startActivity(new Intent(ProfileActivity_2.this, ProfileActivity_2.class));
    }

    public void recordtabClicked(View view){
        startActivity(new Intent(ProfileActivity_2.this, RecordsActivity.class));
    }
    //    public void friendtabClicked(View view){
    //        startActivity(new Intent(ProfileActivity_2.this, ProfileEditActivity.class));
    //
    //    }

}