package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.json.JSONStringer;

public class LoginActivity extends AppCompatActivity implements OnTaskCompleted{
    EditText et_username;
    EditText et_password;
    Button login_Btn;
    Button register_Btn;
    String msgType;
    String username;
    String firstname;
    String faculty;
    String gender;
    String age;
    String password;
    String email;

    int id;
    String status;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.106"; //using your own IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username=(EditText) findViewById(R.id.username);
        et_password=(EditText) findViewById(R.id.password);
        login_Btn=(Button) findViewById(R.id.loginBtn);
        register_Btn=(Button) findViewById(R.id.registerBtn);
    }

    public void loginClicked(View v){
        if(et_username.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your username.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your password.",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // convert edit text values to string data
            msgType = REQ_DOWNLOAD;

            username = et_username.getText().toString();
            password = et_password.getText().toString();

            // create data in JSON format
            String jsonString = convertToJSON();

            // call AsynTask to perform network operation on separate thread
            HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
            task.execute("http://"+HOST+"/"+DIR+"/retrieve.php",
                    jsonString);
        }
    }

    public void registerClicked(View v){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {

            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("username");
            jsonText.value(username);
            jsonText.key("password");
            jsonText.value(password);
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
                    username = jsonObject.getString("username");
                    firstname = jsonObject.getString("firstname");
                    faculty = jsonObject.getString("faculty");
                    gender = jsonObject.getString("gender");
                    age = jsonObject.getString("age");
                    email = jsonObject.getString("email");
                    password = jsonObject.getString("password");
                    id=jsonObject.getInt("id");
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
        if (msgType.equals(REQ_DOWNLOAD) && status.equals("OK")){
            saveAsPreferences();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"The username and password are invalid, please check and enter again.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void saveAsPreferences(){
        SharedPreferences prefs = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username",username);
        editor.putString("firstname",firstname);
        editor.putString("medicalHistory",faculty);
        editor.putString("gender",gender);
        editor.putString("age",age);
        editor.putString("pw",password);
        editor.putString("email",email);
        editor.putInt("id",id);
        editor.commit();
    }

}
