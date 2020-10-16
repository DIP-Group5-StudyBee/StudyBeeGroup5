package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Calendar;
import java.util.TimeZone;


public class RecordsActivityPast extends AppCompatActivity implements OnTaskCompleted {
    String status;
    String msgType;


    TextView Username;
    TextView Meet1;
    TextView Meet2;
    TextView Meet3;
    TextView StartTime1;
    TextView StartTime2;
    TextView StartTime3;


    String host_name;
    String[] Meet_str =new String[3];
    String[] StartTime_str= new String[3];

    String time;
    int numMeet = 0;


    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "172.20.14.231"; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_past);
        //obtain and display username at the top
        Username =  (TextView) findViewById(R.id.Username_record);
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        host_name = sh.getString("username","");
        Username.setText(host_name);

        //Get all displayed items
        Meet1 = (TextView) findViewById(R.id.meeting1);
        Meet2 = (TextView) findViewById(R.id.meeting2);
        Meet3 = (TextView) findViewById(R.id.meeting3);
        StartTime1 = (TextView) findViewById(R.id.startTime1);
        StartTime2 = (TextView) findViewById(R.id.startTime2);
        StartTime3 = (TextView) findViewById(R.id.startTime3);

        //Get the current time for comparison, for now it doesnt update real time only during oncreate
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar c = Calendar.getInstance(tz);
        time =String.format("%04d" , c.get(Calendar.YEAR))+"-"+
                String.format("%02d" , c.get(Calendar.MONTH)+1)+"-"+
                String.format("%02d" , c.get(Calendar.DAY_OF_MONTH))+" "+
                String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE))+":"+
                String.format("%02d" , c.get(Calendar.SECOND));

        //time is not updated every second but only on create, can be implemeneted butttt
        msgType = REQ_DOWNLOAD;

        //upcoming meetings to be displayed
        // call AsynTask to perform network operation on separate thread
        //for past meeting
        String jsonString = convertToJSON();
        HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
        task.execute("http://"+HOST+"/"+DIR+"/retrievePMeet.php",
                jsonString);
    }
    public String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {

            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("host_name");
            jsonText.value(host_name);
            jsonText.key("start_time");
            jsonText.value(time);
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
                        StartTime_str[i] = jsonObject.getString("start_time"+i);
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
        if(Meet_str[0]!= null){
            Meet1.setText(Meet_str[0]);
            StartTime1.setText(StartTime_str[0]);
        }
        else{
            Meet1.setText("No Meeting Record");
            StartTime1.setText("");
        }
        if(Meet_str[1]!= null){
            Meet2.setText(Meet_str[1]);
            StartTime2.setText(StartTime_str[1]);
        }
        else{
            Meet2.setText("No Meeting Record");
            StartTime2.setText("");
        }
        if(Meet_str[2]!= null){
            Meet3.setText(Meet_str[2]);
            StartTime3.setText(StartTime_str[2]);
        }
        else{
            Meet3.setText("No Meeting Record");
            StartTime3.setText("");
        }

    }

    //on click for past
    public void infotabClicked(View view){
        startActivity(new Intent(RecordsActivityPast.this, ProfileActivity_2.class));
    }

    public void recordtabClicked(View view){
        startActivity(new Intent(RecordsActivityPast.this, RecordsActivity.class));
    }
    public void recordUpcomingtabClicked(View view){
        startActivity(new Intent(RecordsActivityPast.this, RecordsActivity.class));
    }
}