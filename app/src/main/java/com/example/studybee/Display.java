package com.example.studybee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.json.JSONObject;
import org.json.JSONStringer;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.studybee.initsdk.AuthConstants.ip;


public class Display extends AppCompatActivity implements AuthConstants, OnTaskCompleted{
    String username;
    String firstname;
    String status;
    String faculty;
    String email;
    EditText et_firstName;
    String msgType;
    Button SButton;
    ImageButton NButton;
    ImageButton NButton1;
    ImageButton NButton2;

    TextView[] showViews= new TextView[6];

    int j,k;

    TextView[] textViews= new TextView[6];

    // Set host address of the WAMP Server
    public static final String HOST = ip; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_tab);

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

    }

    public void SButtonOnClickHandler(View v){
        if(et_firstName.getText().toString().isEmpty()||et_firstName.getText().toString()== "Enter name")
            Toast.makeText(getApplicationContext(), "Please enter a name.", Toast.LENGTH_LONG).show();
        else {
            // Retrieve the message entered and put into the Intent
            msgType = REQ_DOWNLOAD;
            firstname = et_firstName.getText().toString();
            String jsonString = convertToJSON();
            //access database network
            HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
            task.execute("http://" + HOST + "/" + DIR + "/checkName.php", jsonString);

        }

    }
    public void RefreshButtonOnClickHandler(View v) {
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
        showViews[0] = (TextView) findViewById(R.id.nameList1);
        showViews[1] = (TextView) findViewById(R.id.emailList1);
        showViews[2] = (TextView) findViewById(R.id.nameList2);
        showViews[3] = (TextView) findViewById(R.id.emailList2);
        showViews[4] = (TextView) findViewById(R.id.nameList3);
        showViews[5] = (TextView) findViewById(R.id.nameList3);

        showViews[0].setText("tommy");
        showViews[1].setText("tom@e.ntu.edu.sg");
        showViews[2].setText("Santa");
        showViews[3].setText("San@e.ntu.edu.sg");
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
                int comma = 1;
                comma += countCommas();
                String fusername[] = username.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").replaceAll("\"", "").split(",");
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
                    Drawable defsmall = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image = (ImageButton) findViewById(R.id.imageButtonA);
                    image.setImageDrawable(defsmall);
                    NButton.setVisibility(View.VISIBLE);
                    NButton1.setVisibility(View.INVISIBLE);
                    NButton2.setVisibility(View.INVISIBLE);

                }
                if (comma == 2) {
                    for (k = 1; k <= 1; k++) {
                        textViews[k].setText("");

                    }
                    Drawable defsmall = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image = (ImageButton) findViewById(R.id.imageButtonA);
                    image.setImageDrawable(defsmall);
                    Drawable defsmall1 = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image1 = (ImageButton) findViewById(R.id.imageButtonB);
                    image1.setImageDrawable(defsmall1);
                    NButton.setVisibility(View.VISIBLE);
                    NButton1.setVisibility(View.VISIBLE);
                    NButton2.setVisibility(View.INVISIBLE);
                }
                if (comma == 3) {
                    Drawable defsmall = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image = (ImageButton) findViewById(R.id.imageButtonA);
                    image.setImageDrawable(defsmall);
                    Drawable defsmall1 = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image1 = (ImageButton) findViewById(R.id.imageButtonB);
                    image1.setImageDrawable(defsmall1);
                    Drawable defsmall2 = getResources().getDrawable(R.drawable.defsmall1);
                    ImageButton image2 = (ImageButton) findViewById(R.id.imageButtonC);
                    image2.setImageDrawable(defsmall2);
                    NButton.setVisibility(View.VISIBLE);
                    NButton1.setVisibility(View.VISIBLE);
                    NButton2.setVisibility(View.VISIBLE);
                }
                for (k = 0; k < comma; k++) {
                    textViews[k].setText(fusername[k]);
                }

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