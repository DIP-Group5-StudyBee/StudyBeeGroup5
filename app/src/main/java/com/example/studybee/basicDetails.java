package com.example.studybee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONStringer;

public class basicDetails extends AppCompatActivity{

    TextView tname;
    TextView tfaculty;
    TextView temail;
    String msgType;
    String username, sfacu, sname,smail;
    String status;

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.106"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        Bundle bn = getIntent().getExtras();
        username = bn.getString("user");
        sname = bn.getString("name");
        sfacu = bn.getString("facu");
        smail = bn.getString("email");
        tname = findViewById(R.id.textView13);
        tfaculty = findViewById(R.id.textView14);
        temail = findViewById(R.id.textView15);

        tname.setText(sname);
        temail.setText(smail);
        tfaculty.setText(sfacu);


        }
    public void SButtonOnClickHandler(View v){
        Toast.makeText(getApplicationContext(), "Successfully Added.", Toast.LENGTH_LONG).show();
    }

}