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

import org.json.JSONObject;
import org.json.JSONStringer;

import static com.example.studybee.initsdk.AuthConstants.ip;


//onTaskCompleted is an interface with onTaskCompleted method that takes in a response string and gives a void result
public class JoinActivity extends AppCompatActivity implements AuthConstants{

    Spinner size_Spinner;
    Spinner style_Spinner;
    Spinner ta_Spinner;
    Spinner fac_Spinner;
    Button SubmitBtn;

    String msgType;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;


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

        size_Spinner= (Spinner) findViewById(R.id.SizeSpinner);
        style_Spinner= (Spinner) findViewById(R.id.StyleSpinner);
        ta_Spinner= (Spinner) findViewById(R.id.TASpinner);
        fac_Spinner= (Spinner) findViewById(R.id.FACSpinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        SubmitBtn=(Button) findViewById(R.id.SubmitBtn);
    }


    public void SubmitClicked(View v){

        if(size_Spinner.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select a group size.",Toast.LENGTH_SHORT).show();
            return;
        } else if(style_Spinner.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select a study style.",Toast.LENGTH_SHORT).show();
            return;
        } else if(ta_Spinner.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select if you want TA or not.",Toast.LENGTH_SHORT).show();
            return;
        } else if(fac_Spinner.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select if you want people from same faculty or not.",Toast.LENGTH_SHORT).show();
            return;
        } else {

            msgType = REQ_UPLOAD;
            groupSize = size_Spinner.getSelectedItem().toString();
            studyStyle = style_Spinner.getSelectedItem().toString();
            teachingAssistant = ta_Spinner.getSelectedItem().toString();
            course = fac_Spinner.getSelectedItem().toString();
            saveAsPreferences();
            startActivity(new Intent(JoinActivity.this, LobbyActivity.class));
        }

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
