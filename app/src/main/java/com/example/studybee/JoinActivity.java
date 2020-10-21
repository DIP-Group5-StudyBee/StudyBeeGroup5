package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends AppCompatActivity implements OnTaskCompleted{

    Spinner SizeSpinner;
    Button btnSubmit, btnQuiet, btnDiscussion, btnTAYes, btnTANo, btnCourseYes, btnCourseNo;

    String msgType;
    String studyStyle, groupSize, teachingAssistant, course;
    String status;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "172.18.44.222"; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1004";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btnQuiet = findViewById(R.id.btnQuiet);
        btnDiscussion = findViewById(R.id.btnDiscussion);

        SizeSpinner = findViewById(R.id.SizeSpinner);
        List<String> size = new ArrayList<>();
        size.add("2-5");
        size.add("6-8");
        size.add("8 and above");

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SizeSpinner.setAdapter(sizeAdapter);

        btnTAYes = findViewById(R.id.btnTAYes);
        btnTANo = findViewById(R.id.btnTANo);

        btnCourseYes = findViewById(R.id.btnCourseYes);
        btnCourseNo = findViewById(R.id.btnCourseNo);

        btnSubmit = findViewById(R.id.SubmitBtn);
    }



    public void SubmitClicked(View v) {


        btnQuiet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studyStyle = btnQuiet.getText().toString();
            }
        });

        btnDiscussion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studyStyle = btnDiscussion.getText().toString();
            }
        });

        btnTAYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                teachingAssistant = btnTAYes.getText().toString();
            }
        });

        btnTANo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                teachingAssistant = btnTANo.getText().toString();
            }
        });

        btnCourseYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                course = btnCourseYes.getText().toString();
            }
        });

        btnCourseNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                course = btnCourseNo.getText().toString();
            }
        });

        groupSize = SizeSpinner.getSelectedItem().toString();

        // convert edit text values to string data
        msgType = REQ_DOWNLOAD;

        // create data in JSON format
        String jsonString = convertToJSON();

        // call AsynTask to perform network operation on separate thread
        HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin( this);
        task.execute("http://" + HOST + "/" + DIR + "/retrieveRoom.php",
                jsonString);

    }

    public String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {

            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
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
        return jsonText.toString();
    }

    public void retrieveFromJSON(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            msgType = jsonObject.getString("type");
            if (msgType.equals(REQ_DOWNLOAD)) {
                status = jsonObject.getString("status");
                if (status.equals("OK")) {
                    studyStyle = jsonObject.getString("study_style");
                    groupSize = jsonObject.getString("group_size");
                    teachingAssistant = jsonObject.getString("ta_requirement");
                    course = jsonObject.getString("course_requirement");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTaskCompleted(String response) {

        // retrieve response information from JSON
        retrieveFromJSON(response);

        // if response is from upload request
        if (msgType.equals(REQ_DOWNLOAD) && status.equals("OK")) {

            startActivity(new Intent(JoinActivity.this, LobbyActivity.class));

        }
    }

}
