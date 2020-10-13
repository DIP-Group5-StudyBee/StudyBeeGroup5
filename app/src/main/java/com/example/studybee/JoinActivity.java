package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends AppCompatActivity {

    Chip chipQuiet, chipDiscussion;
    Chip chipYesTA, chipNoTA;
    Chip chipYesCourse, chipNoCourse;
    Spinner SizeSpinner;
    Button SubmitBtn;

    String msgType;
    String studyStyle, groupSize, teachingAssistant, course;
    String status;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.106"; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1004";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        chipQuiet = findViewById(R.id.cpQuiet);
        chipDiscussion = findViewById(R.id.cpDiscussion);

        SizeSpinner = findViewById(R.id.SizeSpinner);
        List<String> size = new ArrayList<>();
        size.add("2-5");
        size.add("6-8");
        size.add("8 and above");

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SizeSpinner.setAdapter(sizeAdapter);

        chipYesTA = findViewById(R.id.cpYesTA);
        chipNoTA = findViewById(R.id.cpNoTA);

        chipYesCourse = findViewById(R.id.cpYesCourse);
        chipNoCourse = findViewById(R.id.cpNoCourse);

        SubmitBtn = findViewById(R.id.SubmitBtn);

    }

    public void SubmitClicked(View v) {

        startActivity(new Intent(JoinActivity.this, LobbyActivity.class));

        // convert edit text values to string data
//        msgType = REQ_DOWNLOAD;
//
//        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                if (chipQuiet.isChecked()) {
//                    studyStyle = chipQuiet.getText().toString();
//                }
//                if (chipDiscussion.isChecked()) {
//                    studyStyle = chipQuiet.getText().toString();
//                }
//                if (chipYesTA.isChecked()) {
//                    teachingAssistant = chipQuiet.getText().toString();
//                }
//                if (chipNoTA.isChecked()) {
//                    teachingAssistant = chipQuiet.getText().toString();
//                }
//                if (chipYesCourse.isChecked()) {
//                    course = chipQuiet.getText().toString();
//                }
//                if (chipNoCourse.isChecked()) {
//                    course = chipQuiet.getText().toString();
//                }
//            }
//        };
//        chipQuiet.setOnCheckedChangeListener(checkedChangeListener);
//        chipDiscussion.setOnCheckedChangeListener(checkedChangeListener);
//        chipYesTA.setOnCheckedChangeListener(checkedChangeListener);
//        chipNoTA.setOnCheckedChangeListener(checkedChangeListener);
//        chipYesCourse.setOnCheckedChangeListener(checkedChangeListener);
//        chipNoCourse.setOnCheckedChangeListener(checkedChangeListener);
//
//        groupSize = SizeSpinner.getSelectedItem().toString();
//
//        // create data in JSON format
//        String jsonString = convertToJSON();
//
//        // call AsynTask to perform network operation on separate thread
//        HttpAsyncTaskForJoin task = new HttpAsyncTaskForJoin(this);
//        task.execute("http://" + HOST + "/" + DIR + "/retrieveMeeting.php",
//                jsonString);
//
//    }
//
//    public String convertToJSON() {
//        JSONStringer jsonText = new JSONStringer();
//        try {
//
//            jsonText.object();
//            jsonText.key("type");
//            jsonText.value(msgType);
//            jsonText.key("study_style");
//            jsonText.value(studyStyle);
//            jsonText.key("group_size");
//            jsonText.value(groupSize);
//            jsonText.key("ta_requirement");
//            jsonText.value(teachingAssistant);
//            jsonText.key("course_requirement");
//            jsonText.value(course);
//            jsonText.endObject();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jsonText.toString();
//    }
//
//    public void retrieveFromJSON(String message) {
//        try {
//            JSONObject jsonObject = new JSONObject(message);
//            msgType = jsonObject.getString("type");
//            if (msgType.equals(REQ_DOWNLOAD)) {
//                status = jsonObject.getString("status");
//                if (status.equals("OK")) {
//                    studyStyle = jsonObject.getString("study_style");
//                    groupSize = jsonObject.getString("group_size");
//                    teachingAssistant = jsonObject.getString("ta_requirement");
//                    course = jsonObject.getString("course_requirement");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onTaskCompleted(String response) {
//
//        // retrieve response information from JSON
//        retrieveFromJSON(response);
//
//        // if response is from upload request
//        if (msgType.equals(REQ_DOWNLOAD) && status.equals("OK")) {
//            saveAsPreferences();
//
//            finish();
//        }
//    }
//
//    public void saveAsPreferences() {
//        SharedPreferences prefs = getSharedPreferences("preference", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("study_style", studyStyle);
//        editor.putString("group_size", groupSize);
//        editor.putString("ta_requirement", teachingAssistant);
//        editor.putString("course_requirement", course);
//        editor.commit();
//    }
    }
}