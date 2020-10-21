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

import org.json.JSONObject;
import org.json.JSONStringer;

public class RegisterActivity extends AppCompatActivity implements OnTaskCompleted{
    EditText et_username;
    EditText et_firstName;
    EditText et_email;
    Spinner sp_gender = null;
    EditText et_age;
    EditText et_faculty;
    EditText et_password;
    EditText et_confirmPassword;
    Button confirm;
    String msgType;
    Spinner sp_acc = null;
    String username;
    String firstname;
    String email;
    String gender;
    String age;
    String faculty;
    String password;
    String isTA;

    int id;
    String status,taskOption;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.1.106"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_UPLOAD = "1001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username=(EditText) findViewById(R.id.username);
        et_firstName=(EditText) findViewById(R.id.firstName);
        et_email=(EditText) findViewById(R.id.email);
        sp_gender = (Spinner) findViewById(R.id.spin_Gender);
        et_age=(EditText) findViewById(R.id.age);
        et_faculty=(EditText) findViewById(R.id.faculty);
        et_password=(EditText) findViewById(R.id.password);
        et_confirmPassword=(EditText) findViewById(R.id.confirmPassword);
        confirm=(Button) findViewById(R.id.confirmBtn);
        sp_acc =(Spinner) findViewById(R.id.spin_Account);
}

    public void confirmClicked(View v){

        if(et_username.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your username.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_firstName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your first name.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"Please enter valid email.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!isSchoolEmail(et_email.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please enter your school email.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(sp_gender.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your gender.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_age.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your age.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(et_confirmPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter confirm password.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!et_password.getText().toString().equals(et_confirmPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"The password and confirm password are different, please check.",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            // convert edit text values to string data
            msgType = REQ_UPLOAD;

            username = et_username.getText().toString();
            firstname = et_firstName.getText().toString();
            email = et_email.getText().toString();
            gender = sp_gender.getSelectedItem().toString();
            age = et_age.getText().toString();

            isTA = sp_acc.getSelectedItem().toString();


            if(!et_faculty.getText().toString().isEmpty()){
                faculty = et_faculty.getText().toString();
            }
            else{
                faculty=null;
            }
            password = et_password.getText().toString();

            taskOption="checkUsername";
            String jsonString = convertToJSON();
            HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
            task.execute("http://"+HOST+"/"+DIR+"/checkUsername.php",
                    jsonString);
        }
    }

    public boolean isSchoolEmail(String email){
        if(email.contains(("e.ntu.edu.sg"))){
            return true;
        }
        return false;
    }

    public String convertToJSON() {
        JSONStringer jsonText = new JSONStringer();
        try {

            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("username");
            jsonText.value(username);
            jsonText.key("firstname");
            jsonText.value(firstname);
            jsonText.key("email");
            jsonText.value(email);
            jsonText.key("gender");
            jsonText.value(gender);
            jsonText.key("age");
            jsonText.value(age);
            jsonText.key("faculty");
            jsonText.value(faculty);
            jsonText.key("isTA");
            jsonText.value(isTA);
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
            if (msgType.equals(REQ_UPLOAD)) {
                id = jsonObject.getInt("id");
                firstname = jsonObject.getString("firstname");
                faculty = jsonObject.getString("faculty");
                gender = jsonObject.getString("gender");
                age = jsonObject.getString("age");
                email = jsonObject.getString("email");
                password = jsonObject.getString("password");
                isTA = jsonObject.getString("isTA");
            }
            status = jsonObject.getString("status");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean retrieveFromJSONForUsernameChecking(String message) {
        boolean result=false;
        try {
            JSONObject jsonObject = new JSONObject(message);
            if (jsonObject.getString("result").equals("Unique")) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onTaskCompleted(String response) {

        switch (taskOption){
            case "checkUsername":
                if(!retrieveFromJSONForUsernameChecking(response)){
                    Toast.makeText(getApplicationContext(),"The username was taken, please use another one.",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    taskOption="createProfile";
                    String jsonString = convertToJSON();
                    HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
                    task.execute("http://"+HOST+"/"+DIR+"/register.php",
                            jsonString);
                }
                break;

            case "createProfile":
                retrieveFromJSON(response);
                if (msgType.equals(REQ_UPLOAD)){
                    saveAsPreferences();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(),"Profile created successfully!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Profile creation failed please try again later!",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void saveAsPreferences(){
        SharedPreferences prefs = getSharedPreferences("preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username",username);
        editor.putString("firstname",firstname);
        editor.putString("pw",password);
        editor.putString("faculty",faculty);
        editor.putString("gender",gender);
        editor.putString("age",age);
        editor.putString("email",email);
        editor.putString("isTA",isTA);
        editor.putInt("id",id);
        editor.commit();
    }
}


