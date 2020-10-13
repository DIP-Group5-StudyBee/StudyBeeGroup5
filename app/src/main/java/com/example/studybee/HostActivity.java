package com.example.studybee;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;


//onTaskCompleted is an interface with onTaskCompleted method that takes in a response string and gives a void result
public class HostActivity extends AppCompatActivity implements OnTaskCompleted {
    EditText RoomName;
    EditText RoomDescription;
    Spinner SizeSpinner;
    Chip chipQuiet;
    Chip chipDiscussion;
    Chip chipYesTA;
    Chip chipNoTA;
    Chip chipYesCourse;
    Chip chipNoCourse;
    Button SubmitBtn;

    String msgType;
    String roomName;
    String roomDescription;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;

    String status;

    //returns the name of the class as written in source file
    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.106"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_UPLOAD = "1003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        RoomName=(EditText) findViewById(R.id.RoomName);
        RoomDescription=(EditText) findViewById(R.id.RoomDescription);
        SizeSpinner= (Spinner) findViewById(R.id.SizeSpinner);
        List<String> size = new ArrayList<>();
        size.add("2-5");
        size.add("6-8");
        size.add("8 and above");
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SizeSpinner.setAdapter(sizeAdapter);

        chipQuiet=(Chip) findViewById(R.id.cpQuiet);
        chipDiscussion=(Chip) findViewById(R.id.cpDiscussion);
        chipYesTA=(Chip) findViewById(R.id.cpYesTA);
        chipNoTA=(Chip) findViewById(R.id.cpNoTA);
        chipYesCourse = (Chip) findViewById(R.id.cpYesCourse);
        chipNoCourse = (Chip) findViewById(R.id.cpNoCourse);
        SubmitBtn=(Button) findViewById(R.id.SubmitBtn);
    }

    //method for when clicking register?
    public void SubmitClicked(View v){

        if(RoomName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a room name.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(RoomDescription.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a room description.",Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            //msgType = REQ_UPLOAD = "1001" which is a string
            // convert edit text values to string data
            msgType = REQ_UPLOAD;

            CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (chipQuiet.isChecked()) {

                        studyStyle = chipQuiet.getText().toString();
                    }
                    if (chipDiscussion.isChecked()) {
                        studyStyle = chipQuiet.getText().toString();
                    }
                    if (chipYesTA.isChecked()) {
                        teachingAssistant = chipQuiet.getText().toString();
                    }
                    if (chipNoTA.isChecked()) {
                        teachingAssistant = chipQuiet.getText().toString();
                    }
                    if (chipYesCourse.isChecked()) {
                        course = chipQuiet.getText().toString();
                    }
                    if (chipNoCourse.isChecked()) {
                        course = chipQuiet.getText().toString();
                    }
                }
            };
            chipQuiet.setOnCheckedChangeListener(checkedChangeListener);
            chipDiscussion.setOnCheckedChangeListener(checkedChangeListener);
            chipYesTA.setOnCheckedChangeListener(checkedChangeListener);
            chipNoTA.setOnCheckedChangeListener(checkedChangeListener);
            chipYesCourse.setOnCheckedChangeListener(checkedChangeListener);
            chipNoCourse.setOnCheckedChangeListener(checkedChangeListener);


            groupSize = SizeSpinner.getSelectedItem().toString();

            if(!RoomName.getText().toString().isEmpty()){
                roomName = RoomName.getText().toString();
            }
            else{
                roomName=null;
            }

            if(!RoomDescription.getText().toString().isEmpty()){
                roomDescription = RoomDescription.getText().toString();
            }
            else{
                roomDescription=null;
            }


            String jsonString = convertToJSON(); //create JSON object?
            HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
            task.execute("http://"+HOST+"/"+DIR+"/registerEvent.php",
                    jsonString); //check for any duplicate username entries?
        }
    }


    public String convertToJSON() { //convert to JSON syntax?
        JSONStringer jsonText = new JSONStringer();
        try {


            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("meeting_name");
            jsonText.value(roomName);
            jsonText.key("room_description");
            jsonText.value(roomDescription);
            jsonText.key("study_style");
            jsonText.value(studyStyle);
            jsonText.key("group_size");
            jsonText.value(groupSize);
            jsonText.key("ta_requirement");
            jsonText.value(teachingAssistant);
            jsonText.key("course_requirement");
            jsonText.value(course);
            jsonText.endObject();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString(); //into JSON syntax
    }

    public void retrieveFromJSON(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            msgType = jsonObject.getString("type");
            if (msgType.equals(REQ_UPLOAD)) {
                studyStyle = jsonObject.getString("study_style");
                groupSize = jsonObject.getString("group_size");
                teachingAssistant = jsonObject.getString("ta_requirement");
                course = jsonObject.getString("course_requirement");
                roomName = jsonObject.getString("meeting_name");
                roomDescription = jsonObject.getString("room_description");

            }
            status = jsonObject.getString("status");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTaskCompleted(String response) {


        retrieveFromJSON(response);
        if (msgType.equals(REQ_UPLOAD)){
            saveAsPreferences();
            startActivity(new Intent(HostActivity.this, ZoomScheduleActivity.class));
            Toast.makeText(getApplicationContext(),"Room created seccessfully!",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Room creation failed please try again later!",Toast.LENGTH_LONG).show();
        }

    }

    public void saveAsPreferences(){
        SharedPreferences prefs = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("study_style", studyStyle);
        editor.putString("group_size", groupSize);
        editor.putString("ta_requirement", teachingAssistant);
        editor.putString("course_requirement", course);
        editor.putString("meeting_name", roomName);
        editor.putString("room_description", roomDescription);

        editor.commit();
    }
}

