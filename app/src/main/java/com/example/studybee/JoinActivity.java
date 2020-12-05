package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import org.json.JSONObject;
import org.json.JSONStringer;

import static com.example.studybee.initsdk.AuthConstants.ip;


//onTaskCompleted is an interface with onTaskCompleted method that takes in a response string and gives a void result
public class JoinActivity extends AppCompatActivity implements AuthConstants{

    Chip chipQuiet, chipDiscussion;
    Chip chipLessThanFive, chipLessThanEight, chipAboveEight;
    Chip chipYesTA, chipNoTA;
    Chip chipYesCourse, chipNoCourse;
    Button SubmitBtn;

    String msgType;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;

    String host_name;



    //returns the name of the class as written in source file
    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = ip; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_UPLOAD = "1003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //get username to be updated into meetingevent table
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        host_name = sh.getString("username","");


        chipQuiet=(Chip) findViewById(R.id.cpQuiet);
        chipDiscussion=(Chip) findViewById(R.id.cpDiscussion);
        chipLessThanFive=(Chip) findViewById(R.id.cpLessThanFive);
        chipLessThanEight=(Chip) findViewById(R.id.cpLessThanEight);
        chipAboveEight=(Chip) findViewById(R.id.cpAboveEight);
        chipYesTA=(Chip) findViewById(R.id.cpYesTA);
        chipNoTA=(Chip) findViewById(R.id.cpNoTA);
        chipYesCourse=(Chip) findViewById(R.id.cpYesCourse);
        chipNoCourse=(Chip) findViewById(R.id.cpNoCourse);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        SubmitBtn=(Button) findViewById(R.id.SubmitBtn);
    }


    public void SubmitClicked(View v){

        msgType = REQ_UPLOAD;

        if(chipQuiet.isChecked()) {
            studyStyle = chipQuiet.getText().toString();
        }
        if(chipDiscussion.isChecked()) {
            studyStyle = chipDiscussion.getText().toString();
        }
        if(chipLessThanFive.isChecked()) {
            groupSize = chipLessThanFive.getText().toString();
        }
        if(chipLessThanEight.isChecked()) {
            groupSize = chipLessThanEight.getText().toString();
        }
        if(chipAboveEight.isChecked()) {
            groupSize = chipAboveEight.getText().toString();
        }
        if (chipYesTA.isChecked()) {
            teachingAssistant = chipYesTA.getText().toString();
        }
        if (chipNoTA.isChecked()) {
            teachingAssistant = chipNoTA.getText().toString();
        }
        if (chipYesCourse.isChecked()) {
            course = chipYesCourse.getText().toString();
        }
        if (chipNoCourse.isChecked()) {
            course = chipNoCourse.getText().toString();
        }



        saveAsPreferences();
        startActivity(new Intent(JoinActivity.this, LobbyActivity.class));


    }

    public void saveAsPreferences(){
        SharedPreferences prefs = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msgType",msgType);
        editor.putString("groupSize",groupSize);
        editor.putString("studyStyle",studyStyle);
        editor.putString("teachingAssistant",teachingAssistant);
        editor.putString("course",course);
        editor.commit();
    }

}
