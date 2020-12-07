package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybee.adapter.UpcomingMeetingAdapter;
import com.example.studybee.model.UpcomingMeeting;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;
import static com.example.studybee.initsdk.AuthConstants.ip;


public class HomeFragment extends Fragment implements OnTaskCompleted {

    private RecyclerView upcomingMeetingRv;
    private UpcomingMeetingAdapter upcomingMeetingAdapter;
    private CardView createButton, joinButton;
    private String isTA;

    String host_name;
    int user_id;
    String[] Meet_str =new String[3];
    String[] StartTime_str= new String[3];

    String time;
    int numMeet = 0;

    String status;
    String msgType;


    String Username;
    TextView Meet1;
    TextView Meet2;
    TextView Meet3;
    TextView StartTime1;
    TextView StartTime2;
    TextView StartTime3;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = ip; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1004";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        upcomingMeetingRv = rootView.findViewById(R.id.upcoming_meetings_rv);

//        Username =  (TextView) findViewById(R.id.Username_record);
        SharedPreferences sh = this.getActivity().getSharedPreferences("preference", MODE_PRIVATE);
//        Username = sh.getString("username", "");
        host_name = sh.getString("username","");
        user_id = sh.getInt("id",0);

        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar c = Calendar.getInstance(tz);
        time =String.format("%04d" , c.get(Calendar.YEAR))+"-"+
                String.format("%02d" , c.get(Calendar.MONTH)+1)+"-"+
                String.format("%02d" , c.get(Calendar.DAY_OF_MONTH))+" "+
                String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE))+":"+
                String.format("%02d" , c.get(Calendar.SECOND));

        msgType = REQ_DOWNLOAD;

        String jsonString = convertToJSON();
        HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
        task.execute("http://"+HOST+"/"+DIR+"/retrieveUMeet.php",
                jsonString);

        //set up dummy data for model class for upcoming meetings
//        List<UpcomingMeeting> upcomingMeetingList = new ArrayList<>();
//
//        upcomingMeetingList.add(new UpcomingMeeting("Title 1", "Java Programming", "12hrs"));
//        upcomingMeetingList.add(new UpcomingMeeting("Title 2", "Engine Comm", "12hrs"));
//        upcomingMeetingList.add(new UpcomingMeeting("Title 3", "Alphabets", "12hrs"));
//
//        setUpcomingRecycler(upcomingMeetingList);

        createButton = rootView.findViewById(R.id.createButton);
        joinButton = rootView.findViewById(R.id.joinButton);

        //OnClick event on create cardView
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getContext(), HostActivity.class));
                }
//            }
        });

        //OnClick event on join cardView
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getContext().getSharedPreferences("preference", MODE_PRIVATE);
                isTA = sh.getString("identity", "");
                if (isTA.equals("Teaching Assistant")) {
                    saveAsPreferences();
                    //Toast.makeText(getApplicationContext(),"Hello lobbyact", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LobbyActivity.class));
                } else {
                    //Toast.makeText(getApplicationContext(),"Hello FAIL", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), JoinActivity.class));
                }

            }
        });

        return rootView;
    }

    public String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("start_time");
            jsonText.value(time);
            jsonText.key("user_id");
            jsonText.value(user_id);
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
            if (msgType.equals(REQ_DOWNLOAD)){
                status = jsonObject.getString("status");
                if(status.equals("OK")){

                    //task option will retrieve the correct only taking 3 values
                    numMeet = jsonObject.getInt("number");
                    for (int i = 0; i<numMeet; i++) {
                        Meet_str[i] = jsonObject.getString("meeting_name"+i);
                        StartTime_str[i] = jsonObject.getString("meeting_time"+i);
                    }
                    for (int i = numMeet +1; i<3; i++){
                        //not value return
                        Meet_str[i] = null;
                        StartTime_str[i] = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTaskCompleted(String response) {
        // retrieve response information from JSON
        retrieveFromJSON(response);

        List<UpcomingMeeting> upcomingMeetingList = new ArrayList<>();

        setUpcomingRecycler(upcomingMeetingList);

        if(Meet_str[0]!= null){

            upcomingMeetingList.add(new UpcomingMeeting("Title 1", Meet_str[0], StartTime_str[0]));
//            Meet1.setText(Meet_str[0]);
//            StartTime1.setText(StartTime_str[0]);
        }
        else{
            upcomingMeetingList.add(new UpcomingMeeting("No meeting", "", ""));
//            Meet1.setText("No Meeting Record");
//            StartTime1.setText("");
        }
        if(Meet_str[1]!= null){
            upcomingMeetingList.add(new UpcomingMeeting("Title 1", Meet_str[1], StartTime_str[1]));
//            Meet2.setText(Meet_str[1]);
//            StartTime2.setText(StartTime_str[1]);
        }
        else{
            upcomingMeetingList.add(new UpcomingMeeting("No meeting", "", ""));
//            Meet2.setText("No Meeting Record");
//            StartTime2.setText("");
        }
        if(Meet_str[2]!= null){
            upcomingMeetingList.add(new UpcomingMeeting("Title 1", Meet_str[2], StartTime_str[2]));
//            Meet3.setText(Meet_str[2]);
//            StartTime3.setText(StartTime_str[2]);
        }
        else{
            upcomingMeetingList.add(new UpcomingMeeting("No meeting", "", ""));
//            Meet3.setText("No Meeting Record");
//            StartTime3.setText("");
        }

    }

    //Method to attach the MeetingList with RecyclerView with dummy data
    private void setUpcomingRecycler(List<UpcomingMeeting> upcomingMeetingList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        upcomingMeetingRv.setLayoutManager(layoutManager);
        upcomingMeetingAdapter = new UpcomingMeetingAdapter(getContext(), upcomingMeetingList);
        upcomingMeetingRv.setAdapter(upcomingMeetingAdapter);
    }

    public void saveAsPreferences() {
        //HOW TO ADD WILDCARD TO SELECTION???!!!!
        SharedPreferences prefs = getContext().getSharedPreferences("preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msgType", "%");
        editor.putString("groupSize", "%");
        editor.putString("studyStyle", "%");
        editor.putString("teachingAssistant", "Yes");
        editor.putString("course", "%");
        editor.commit();
    }

}