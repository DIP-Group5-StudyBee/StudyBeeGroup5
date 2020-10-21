package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.json.JSONStringer;

public class ProfileEditActivity extends AppCompatActivity implements OnTaskCompleted {
    EditText e_username;
    EditText e_firstName;
    EditText e_email;
    Spinner e_gender = null;
    EditText e_age;
    EditText e_faculty;
    EditText e_password;
    TextView profile_name;
    ImageButton confirm;
    String msgType;
    String username;
    String firstname;
    String email;
    String gender;
    String age;
    String faculty;
    String password;
    String initial_username;

    int id;
    String status,taskOption;

    private final String TAG = this.getClass().getSimpleName();

    // Set host address of the WAMP Server
    public static final String HOST = "192.168.0.105"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_UPLOAD = "1003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        e_username=(EditText) findViewById(R.id.edit_username);
        e_firstName=(EditText) findViewById(R.id.edit_name);
        e_email=(EditText) findViewById(R.id.edit_email);
        e_gender = (Spinner) findViewById(R.id.edit_gender);
        e_age=(EditText) findViewById(R.id.edit_age);
        e_faculty=(EditText) findViewById(R.id.edit_school);
        e_password=(EditText) findViewById(R.id.edit_password);
        profile_name = (TextView) findViewById(R.id.profile_name);
        confirm=(ImageButton) findViewById(R.id.save_button);

        //load the int id incase they need to change the values
        SharedPreferences sh = getSharedPreferences("preference", MODE_PRIVATE);
        id= sh.getInt("id",0);
        initial_username = sh.getString("username","");
        profile_name.setText(initial_username);

    }
    public void submitClicked(View v){

        if(e_username.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your username.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(e_firstName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your first name.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e_email.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"Please enter valid email.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!isSchoolEmail(e_email.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please enter your school email.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(e_gender.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your gender.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(e_age.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your age.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(e_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter your age.",Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            // convert edit text values to string data
            msgType = REQ_UPLOAD;

            username = e_username.getText().toString();
            firstname = e_firstName.getText().toString();
            email = e_email.getText().toString();
            gender = e_gender.getSelectedItem().toString();
            age = e_age.getText().toString();
            faculty = e_faculty.getText().toString();
            password = e_password.getText().toString();

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
            jsonText.key("id");
            jsonText.value(id);
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
                firstname = jsonObject.getString("firstname");
                faculty = jsonObject.getString("faculty");
                gender = jsonObject.getString("gender");
                age = jsonObject.getString("age");
                email = jsonObject.getString("email");
                password = jsonObject.getString("password");
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
                    taskOption="updateProfile";
                    String jsonString = convertToJSON();
                    HttpAsyncTaskForLogin task = new HttpAsyncTaskForLogin(this);
                    task.execute("http://"+HOST+"/"+DIR+"/updateProfile.php",
                            jsonString);
                }
                break;

            case "updateProfile":
                retrieveFromJSON(response);
                if (msgType.equals(REQ_UPLOAD)){
                    saveAsPreferences();
                    startActivity(new Intent(ProfileEditActivity.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(),"Profile updated successfully!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Profile updated failed please try again later!",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //save new perferences
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
        editor.putInt("id",id);
        editor.commit();
    }

    //For bottom navigation bar

//    public void homeBotClicked(View view){
//        startActivity(new Intent(ProfileEditActivity.this, MainActivity.class));
//
//    }
//    public void profileBotClicked(View view){
//        startActivity(new Intent(ProfileEditActivity.this, ProfileActivity_2.class));
//
//    }
    //    public void joinBotClicked(View view){
    //        startActivity(new Intent(ProfileActivity_2.this, ProfileEditActivity.class));
    //
    //    }

    //Middle navigation tab

    public void infotabClicked(View view){
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
        startActivity(new Intent(ProfileEditActivity.this, MainActivity.class));

    }
    public void recordtabClicked(View view){
        startActivity(new Intent(ProfileEditActivity.this, RecordsActivity.class));
    }
    //    public void friendtabClicked(View view){
    //        startActivity(new Intent(ProfileActivity_2.this, ProfileEditActivity.class));
    //
    //    }

}