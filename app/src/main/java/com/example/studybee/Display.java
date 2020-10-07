package com.example.studybee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.style.AlignmentSpan;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.*;


public class Display extends AppCompatActivity implements OnTaskCompleted{
    String username;
    String firstname;
    String status;
    String faculty;
    String email;
    EditText et_firstName;
    SearchView search;
    String msgType;
    Button SButton;
    ImageButton NButton;
    ImageButton NButton1;
    ImageButton NButton2;

    int j,k;
    private final String TAG = this.getClass().getSimpleName();
    boolean test = false;

    TextView[] textViews= new TextView[6];

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.108"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        //String jsonString = convertToJSON();
        //Toast.makeText(getApplicationContext(), firstname, Toast.LENGTH_LONG).show();
        //access database network
        //HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
        //task.execute("http://" + HOST + "/" + DIR + "/checkName.php", jsonString);

        et_firstName = (EditText) findViewById(R.id.editTextTextPersonName);
        SButton = findViewById(R.id.button);

        NButton = findViewById(R.id.imageButtonA);//link to basic details page
        NButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[0].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(Display.this,basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[0]).putExtra("facu",tfacu[0]).putExtra("email",tmail[0]);
                startActivity(intent);
                finish();
            }
        });
        NButton1 = findViewById(R.id.imageButtonB);//link to basic details page
        NButton1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[1].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(Display.this,basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[1]).putExtra("facu",tfacu[1]).putExtra("email",tmail[1]);
                startActivity(intent);
                finish();
            }
        });
        NButton2 = findViewById(R.id.imageButtonC);//link to basic details page
        NButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[2].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(Display.this,basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[2]).putExtra("facu",tfacu[2]).putExtra("email",tmail[2]);
                startActivity(intent);
                finish();
            }
        });




        //Intent intent = new Intent(this, Friends.class);
        // Retrieve the message entered and put into the Intent
    }

    public void SButtonOnClickHandler(View v){
        if(et_firstName.getText().toString().isEmpty()||et_firstName.getText().toString()== "Enter name")
            Toast.makeText(getApplicationContext(), "Please enter a name.", Toast.LENGTH_LONG).show();
        else {
            //Intent intent = new Intent(this, Friends.class);
            // Retrieve the message entered and put into the Intent
            msgType = REQ_DOWNLOAD;
            firstname = et_firstName.getText().toString();
            String jsonString = convertToJSON();
            //Toast.makeText(getApplicationContext(), firstname, Toast.LENGTH_LONG).show();
            //access database network
            HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
            task.execute("http://" + HOST + "/" + DIR + "/checkName.php", jsonString);

        }

    }

    private String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {

            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("username");
            jsonText.value(username);
            jsonText.key("firstname");
            jsonText.value(firstname);
            jsonText.key("faculty");
            jsonText.value(faculty);
            jsonText.key("email");
            jsonText.value(email);
            jsonText.endObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }



public void retrieveFromJSON(String message) {
        try {
            int i =0;
            JSONObject jsonObject = new JSONObject(message);
            msgType = jsonObject.getString("type");
            if (msgType.equals(REQ_DOWNLOAD)) {
            status = jsonObject.getString("status");
            if (status.equals("OK")) {
            username = jsonObject.getString("username");
            firstname = jsonObject.getString("firstname");
            faculty = jsonObject.getString("faculty");
            email = jsonObject.getString("email");
            }
        }

        } catch (Exception e) {
            e.printStackTrace();
            }
    }
    @Override
    public void onTaskCompleted(String response) {

        //get data from database
        retrieveFromJSON(response);
        if ((msgType.equals(REQ_DOWNLOAD)) && status.equals("OK")) {
            //display data
            //Toast.makeText(getApplicationContext(), "No match found!2", Toast.LENGTH_SHORT).show();
            int comma = 1;
            comma += countCommas();
            String fusername[] = username.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
            //Toast.makeText(getApplicationContext(), fusername[0], Toast.LENGTH_LONG).show();
            textViews[0] = (TextView) findViewById(R.id.textViewA);
            textViews[1] = (TextView) findViewById(R.id.textViewB);
            textViews[2] = (TextView) findViewById(R.id.textViewC);
            NButton = findViewById(R.id.imageButtonA);
            NButton1 = findViewById(R.id.imageButtonB);
            NButton2 = findViewById(R.id.imageButtonC);

            if (comma == 1) {
                for (k = 1; k <= 2; k++) {
                    textViews[k].setText("");

                }
                NButton.setVisibility(View.VISIBLE);
                NButton1.setVisibility(View.INVISIBLE);
                NButton2.setVisibility(View.INVISIBLE);

            }
            if (comma == 2) {
                for (k = 1; k <= 1; k++) {
                    textViews[k].setText("");

                }
                NButton.setVisibility(View.VISIBLE);
                NButton1.setVisibility(View.VISIBLE);
                NButton2.setVisibility(View.INVISIBLE);
            }
            if(comma ==3)
            {
                NButton.setVisibility(View.VISIBLE);
                NButton1.setVisibility(View.VISIBLE);
                NButton2.setVisibility(View.VISIBLE);
            }
            for (k = 0; k < comma; k++) {
                textViews[k].setText(fusername[k]);
            }



           /* TextView txtDisplay = (TextView) findViewById(R.id.textView2);//set display text
        txtDisplay.setText(fusername[0]);//display text
            TextView txtDisplay1 = (TextView) findViewById(R.id.textView3);//set display text
            txtDisplay1.setText(fusername[1]);//display text
            TextView txtDisplay2 = (TextView) findViewById(R.id.textView4);//set display text
            txtDisplay2.setText(fusername[2]);//display text*/

        } else {
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        return;
        }

        }
        public int countCommas(){
            int commas =0;
            for(j =0; j<username.length();j++)
            {
                if (username.charAt(j) == ',')
                    commas++;
            }
            return commas;
        }


}