package com.example.studybee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;
import us.zoom.sdk.StartMeetingOptions;

public class LobbyActivity extends AppCompatActivity implements OnTaskCompleted {
    String meeting_name;
    String group_size;
    String start_time;
    String room_description;
    String host_name;
    String zoom_id;
    String zoom_pw;
    String msgType;
    String study_style;
    String teaching_assistant;
    String Course;
    String isTA;
    String meeting_id;
    String user_id;

    int id;
    String groupSize;
    String studyStyle;
    String teachingAssistant;
    String course;
    int flag = 0;

    ArrayList<MeetingEventObj> meetingEvents = new ArrayList<MeetingEventObj>();
    Button closePopupBtn, joinPopupBtn;
    TextView poptxt;
    PopupWindow popupWindow;

    String status;
    TableLayout tbmeetingEvent;
    private final String tag = this.getClass().getSimpleName();
    // Set host address of the WAMP Server
    public static final String HOST = "192.168.86.178"; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1001";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        tbmeetingEvent = (TableLayout) findViewById(R.id.tbmeetingEvent);

        SharedPreferences meetingTable = getSharedPreferences("preference", MODE_PRIVATE);
        groupSize = meetingTable.getString("groupSize", "");
        studyStyle = meetingTable.getString("studyStyle", "");
        teachingAssistant = meetingTable.getString("teachingAssistant", "");
        course = meetingTable.getString("course", "");
//        Toast.makeText(getApplicationContext(),groupSize,Toast.LENGTH_LONG).show();


        msgType = REQ_DOWNLOAD;
        // create data in JSON format
        String jsonString = convertToJSON();
        HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
        task.execute("http://" + HOST + "/" + DIR + "/retrieveMeeting.php", jsonString);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTaskCompleted(String response) {
        // retrieve response information from JSON
        retrieveFromJSON(response);
        refreshTable();
    }


    public void retrieveFromJSON(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray resultArray = jsonObject.getJSONArray("results");
            meetingEvents.clear();
            msgType = jsonObject.getString("type");
            if (msgType.equals(REQ_DOWNLOAD)) {
                status = jsonObject.getString("status");
                if (status.equals("OK")) {
//                    Toast.makeText(getApplicationContext(),"Im ok",Toast.LENGTH_LONG).show();
                    if (resultArray != null) {
//                        Toast.makeText(getApplicationContext(),String.valueOf(resultArray.length()),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject resultObj = resultArray.getJSONObject(i);
                            group_size = resultObj.getString("group_size");
//                            teaching_assistant = jsonObject.getString("ta_requirement");
                            meeting_name = resultObj.getString("meeting_name");
                            start_time = resultObj.getString("start_time");
                            host_name = resultObj.getString("host_name");
                            room_description = resultObj.getString("room_description");
                            zoom_id = resultObj.getString("zoom_id");
                            zoom_pw = resultObj.getString("zoom_pw");
                            meeting_id = resultObj.getString("meeting_id");
                            meetingEvents.add(new MeetingEventObj(meeting_name, group_size, start_time, room_description, host_name, zoom_id, zoom_pw,meeting_id));
//                            Toast.makeText(getApplicationContext(),String.valueOf(meetingEvents.size()),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void refreshTable() {
        Log.d(tag, "refreshTable...");
        tbmeetingEvent.removeAllViews();

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView title = new TextView(this);
        title.setText("Rooms");
        title.setTextColor(Color.parseColor("#C2185B"));
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 3;
        title.setPadding(0, 0, 0, 20);
        rowTitle.addView(title, params);

        tbmeetingEvent.addView(rowTitle);

        // display rows
//        Toast.makeText(getApplicationContext(),meetingEvents.size(),Toast.LENGTH_LONG).show();
        for (int i = 0; i < meetingEvents.size(); i++) {
            final MeetingEventObj meetingEvent = meetingEvents.get(i);

            TableRow.LayoutParams imageParams;
            imageParams = new TableRow.LayoutParams(120, 120);
            imageParams.gravity = Gravity.LEFT;
            imageParams.column = 0;

            ImageView image = new ImageView((getApplicationContext()));
            image.setImageResource(R.drawable.done);
            image.setPadding(5, 10, 5, 10);

            TextView nameView = new TextView(getApplicationContext());
            nameView.setText(meetingEvent.meeting_name + "\n" + meetingEvent.host_name + "\n" + meetingEvent.start_time);
            nameView.setTextSize(16);
            nameView.setLines(3);
            nameView.setTextColor(Color.BLACK); // WHITE, if background is black
            TableRow.LayoutParams nameViewParams;
            nameViewParams = new TableRow.LayoutParams(600, 250);
            nameViewParams.gravity = Gravity.LEFT;
            nameViewParams.column = 1;


            Button btnShow = new Button(getApplicationContext());
            btnShow.setText("VIEW");
            btnShow.setPadding(1, 10, 50, 10);
            TableRow.LayoutParams btnShowParams;
            btnShowParams = new TableRow.LayoutParams(230, 130);
            btnShowParams.setMargins(5, 10, 5, 10);

            btnShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //instantiate the popup.xml layout file
                    LayoutInflater layoutInflater = (LayoutInflater) LobbyActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup, null);

                    joinPopupBtn = (Button) customView.findViewById(R.id.joinPopupBtn);
                    closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                    poptxt = (TextView) customView.findViewById(R.id.poptxt);
                    poptxt.setText(meetingEvent.room_description);
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    //display the popup window
                    popupWindow.showAtLocation(tbmeetingEvent, Gravity.CENTER, 0, 0);

                    joinPopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            //Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
                            SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
                            isTA = sh.getString("identity","");

                            //ADD CRITERIA TO CHECK IF TA
                            if (isTA.equals("Teaching Assistant")) {
                                msgType = REQ_DOWNLOAD;
                                // create data in JSON format
                                JSONStringer jsonText = new JSONStringer();
                                try {
                                    jsonText.object();
                                    jsonText.key("type");
                                    jsonText.value(msgType);
                                    jsonText.key("ta_availability");
                                    jsonText.value("Yes");
                                    jsonText.key("room_description");
                                    jsonText.value(meetingEvent.room_description);
                                    jsonText.endObject();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                //here
                                String jsonString = jsonText.toString();
                                new HttpAsyncTaskForOnClick(this).execute("http://" + HOST + "/" + DIR + "/updateTA.php", jsonString);

                                //Toast.makeText(getApplicationContext(), "Toasty.", Toast.LENGTH_SHORT).show();

                                //Other Query below

                                // create data in JSON format
                                JSONStringer jsonTextI = new JSONStringer();
                                user_id = String.valueOf(sh.getInt("id",0));
                                try {
                                    jsonTextI.object();
                                    jsonTextI.key("type");
                                    jsonTextI.value(msgType);
                                    jsonTextI.key("user_id");
                                    jsonTextI.value(user_id);
                                    jsonTextI.key("meeting_id");
                                    jsonTextI.value(meetingEvent.meeting_id);
                                    jsonTextI.key("meeting_name");
                                    jsonTextI.value(meetingEvent.meeting_name);
                                    jsonTextI.key("meeting_time");
                                    jsonTextI.value(meetingEvent.start_time);
                                    jsonTextI.endObject();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                //here
                                String jsonStringI = jsonTextI.toString();
                                new HttpAsyncTaskForOnClick(this).execute("http://" + HOST + "/" + DIR + "/insertMeetingrecord.php", jsonStringI);
                            }

                            SharedPreferences prefs = getSharedPreferences("preference", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("zoom_pw", meetingEvent.zoom_pw);
                            editor.putString("zoom_id", meetingEvent.zoom_id);
                            editor.commit();

                            startActivity(new Intent(LobbyActivity.this, ZoomActivity2.class));


                        }
                    });


                    //close the popup window on button click
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(50, 0, 50, 0);

            row.setLayoutParams(tableRowParams);

            row.addView(image, imageParams);
            row.addView(nameView, nameViewParams);
            row.addView(btnShow, btnShowParams);

            tbmeetingEvent.addView(row);
        }


    }// refreshTable


//    public void createScheduleMeeting(View view){
//        startActivity(new Intent(LobbyActivity.this, ZoomScheduleActivity.class));
//    }

}
