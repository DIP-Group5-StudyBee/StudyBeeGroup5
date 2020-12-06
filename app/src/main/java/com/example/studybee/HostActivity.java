//package com.example.studybee;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONObject;
//import org.json.JSONStringer;
//
//
////onTaskCompleted is an interface with onTaskCompleted method that takes in a response string and gives a void result
//public class HostActivity extends AppCompatActivity implements OnTaskCompleted{
//    EditText RoomName;
//    EditText RoomDescription;
//    Spinner size_Spinner;
//    Spinner style_Spinner;
//    Spinner ta_Spinner;
//    Spinner fac_Spinner;
//    Button SubmitBtn;
//
//    String msgType;
//    String roomName;
//    String roomDescription;
//    String groupSize;
//    String studyStyle;
//    String teachingAssistant;
//    String course;
//    String isTA;
//
//    int id;
//    String status;
//
//    //returns the name of the class as written in source file
//    private final String TAG = this.getClass().getSimpleName();
//
//    // Set host address of the WAMP Server
//    public static final String HOST = "192.168.1.104"; //using your own IP address
//
//    // Set virtual directory of the host website
//    public static final String DIR = "myproject";
//
//    // Set request ID for all HTTP requests
//    private static final String REQ_UPLOAD = "1003";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_host);
//
//
//        RoomName=(EditText) findViewById(R.id.RoomName);
//        RoomDescription=(EditText) findViewById(R.id.RoomDescription);
//        size_Spinner= (Spinner) findViewById(R.id.SizeSpinner);
//        style_Spinner= (Spinner) findViewById(R.id.StyleSpinner);
//        ta_Spinner= (Spinner) findViewById(R.id.TASpinner);
//        fac_Spinner= (Spinner) findViewById(R.id.FACSpinner);
//
//        SubmitBtn=(Button) findViewById(R.id.SubmitBtn);
//    }
//
//    //method for when clicking register?
//    public void SubmitClicked(View v){
//
//        if(RoomName.getText().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please enter a room name.",Toast.LENGTH_SHORT).show();
//            return;
//        } else if(RoomDescription.getText().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please enter a room description.",Toast.LENGTH_SHORT).show();
//            return;
//        } else if(size_Spinner.getSelectedItem().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please select a group size.",Toast.LENGTH_SHORT).show();
//            return;
//        } else if(style_Spinner.getSelectedItem().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please select a study style.",Toast.LENGTH_SHORT).show();
//            return;
//        } else if(ta_Spinner.getSelectedItem().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please select if you want TA or not.",Toast.LENGTH_SHORT).show();
//            return;
//        } else if(fac_Spinner.getSelectedItem().toString().isEmpty()){
//            Toast.makeText(getApplicationContext(),"Please select if you want people from same faculty or not.",Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//
//            msgType = REQ_UPLOAD;
//            groupSize = size_Spinner.getSelectedItem().toString();
//            studyStyle = style_Spinner.getSelectedItem().toString();
//            teachingAssistant = ta_Spinner.getSelectedItem().toString();
//            course = fac_Spinner.getSelectedItem().toString();
//            if(!RoomName.getText().toString().isEmpty()){
//                roomName = RoomName.getText().toString();
//            }
//            else{
//                roomName = null;
//            }
//            if(!RoomDescription.getText().toString().isEmpty()){
//                roomDescription = RoomDescription.getText().toString();
//            }
//            else{
//                roomDescription=null;
//            }
//
//            String jsonString = convertToJSON();
//            HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
//            task.execute("http://"+HOST+"/"+DIR+"/createroom.php", jsonString);
//
//        }
//
//    }
//
//
//    public String convertToJSON() { //convert to JSON syntax?
//        JSONStringer jsonText = new JSONStringer();
//        try {
//
//
//            jsonText.object();
//            jsonText.key("type");
//            jsonText.value(msgType);
//            jsonText.key("meeting_name");
//            jsonText.value(roomName);
//            jsonText.key("room_description");
//            jsonText.value(roomDescription);
//            jsonText.key("study_style");
//            jsonText.value(studyStyle);
//            jsonText.key("group_size");
//            jsonText.value(groupSize);
//            jsonText.key("ta_requirement");
//            jsonText.value(teachingAssistant);
//            jsonText.key("course_requirement");
//            jsonText.value(course);
//            SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
//            isTA = sh.getString("identity","");
//            if (isTA.equals("Teaching Assistant")) {
//                jsonText.key("ta_availability");
//                jsonText.value("Yes");
//            } else {
//                jsonText.key("ta_availability");
//                jsonText.value("No");
//            }
//            jsonText.endObject();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jsonText.toString(); //into JSON syntax
//    }
//
//    public void retrieveFromJSON(String message) {
//        try {
//            JSONObject jsonObject = new JSONObject(message);
//            msgType = jsonObject.getString("type");
//            if (msgType.equals(REQ_UPLOAD)) {
//                status = jsonObject.getString("status");
//                if (status.equals("OK")) {
//                    id = jsonObject.getInt("id");
//                    studyStyle = jsonObject.getString("study_style");
//                    groupSize = jsonObject.getString("group_size");
//                    teachingAssistant = jsonObject.getString("ta_requirement");
//                    course = jsonObject.getString("course_requirement");
//                    roomName = jsonObject.getString("meeting_name");
//                    roomDescription = jsonObject.getString("room_description");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void onTaskCompleted(String response) {
//
////        String jsonString = convertToJSON();
////        HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
////        task.execute("http://"+HOST+"/"+DIR+"/createroom.php", jsonString);
//
//        retrieveFromJSON(response);
//        if (msgType.equals(REQ_UPLOAD) && status.equals("OK")){
//
//            startActivity(new Intent(HostActivity.this, ZoomScheduleActivity.class));
//            Toast.makeText(getApplicationContext(),"Room created successfully!",Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Room creation failed please try again later!",Toast.LENGTH_LONG).show();
//
//        }
//
//    }
//
//}
package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybee.ui.InitAuthSDKActivity;
import com.google.android.material.chip.Chip;


import org.json.JSONObject;
import org.json.JSONStringer;

import static com.example.studybee.initsdk.AuthConstants.ip;
//import com.example.studybee.ui.InitAuthSDKActivityCreate;

//onTaskCompleted is an interface with onTaskCompleted method that takes in a response string and gives a void result
public class HostActivity extends AppCompatActivity implements AuthConstants, OnTaskCompleted{
    EditText RoomName;
    EditText RoomDescription;
    Spinner size_Spinner;
    Spinner style_Spinner;
    Spinner ta_Spinner;
    Spinner fac_Spinner;

    Button SubmitBtn;

    String msgType;
    String roomName;
    String roomDescription;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;

    int id;
    String status;
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
        setContentView(R.layout.activity_host);

       //get username to be updated into meetingevent table
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        host_name = sh.getString("username","");


        RoomName=(EditText) findViewById(R.id.RoomName);
        RoomDescription=(EditText) findViewById(R.id.RoomDescription);
        size_Spinner= (Spinner) findViewById(R.id.SizeSpinner);
        style_Spinner= (Spinner) findViewById(R.id.StyleSpinner);
        ta_Spinner= (Spinner) findViewById(R.id.TASpinner);
        fac_Spinner= (Spinner) findViewById(R.id.FACSpinner);

        SubmitBtn=(Button) findViewById(R.id.SubmitBtn);
    }

    //method for when clicking register?
    public void SubmitClicked(View v){

        if(RoomName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a room name.",Toast.LENGTH_SHORT).show();
            return;
        } else if(RoomDescription.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a room description.",Toast.LENGTH_SHORT).show();
            return;
        } else if(size_Spinner.getSelectedItem().toString().isEmpty()){
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
            if(!RoomName.getText().toString().isEmpty()){
                roomName = RoomName.getText().toString();
            }
            else{
                roomName = null;
            }
            if(!RoomDescription.getText().toString().isEmpty()){
                roomDescription = RoomDescription.getText().toString();
            }
            else{
                roomDescription=null;
            }

            String jsonString = convertToJSON();
            HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
            task.execute("http://"+HOST+"/"+DIR+"/createroom.php", jsonString);

        }

    }


    public String convertToJSON() { //convert to JSON syntax?
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
            jsonText.key("host_name");
            jsonText.value(host_name);
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
                status = jsonObject.getString("status");
                if (status.equals("OK")) {
                    id = jsonObject.getInt("id");
                    //host_name = jsonObject.getString("host_name");
                    studyStyle = jsonObject.getString("study_style");
                    groupSize = jsonObject.getString("group_size");
                    teachingAssistant = jsonObject.getString("ta_requirement");
                    course = jsonObject.getString("course_requirement");
                    roomName = jsonObject.getString("meeting_name");
                    roomDescription = jsonObject.getString("room_description");
                    startActivity(new Intent(HostActivity.this, InitAuthSDKActivity.class));
                    Toast.makeText(getApplicationContext(),"Room created successfully!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Room creation failed please try again later!",Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTaskCompleted(String response) {

//        String jsonString = convertToJSON();
//        HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
//        task.execute("http://"+HOST+"/"+DIR+"/createroom.php", jsonString);

        retrieveFromJSON(response);
        if (msgType.equals(REQ_UPLOAD) && status.equals("OK")){
            SharedPreferences meetingCreatePrefs = getSharedPreferences("preference",MODE_PRIVATE);
            SharedPreferences.Editor editor = meetingCreatePrefs.edit();
            editor.putInt("localmeetingid",id);
            editor.commit();
        }
    }

}
