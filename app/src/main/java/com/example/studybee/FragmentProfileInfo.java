package com.example.studybee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.studybee.initsdk.AuthConstants.ip;

public class FragmentProfileInfo extends Fragment {

    TextView dis_firstname;
    TextView dis_gender;
    TextView dis_faculty;
    TextView dis_username;
    TextView dis_age;
    TextView dis_email;
    Button record_Btn;
    Button info_Btn;
    Button fri_Btn;
    ImageButton edit_Btn;


    String username;
    String firstname;
    String faculty;
    String gender;
    String age;
    String email;

    int j,k;
    private final String TAG = this.getClass().getSimpleName();
    boolean test = false;

    TextView[] textViews= new TextView[6];

    // Set host address of the WAMP Server
    public static final String HOST = ip; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_info, container,false);

            dis_firstname=(TextView) myView.findViewById(R.id.full_name);
//            dis_username=(TextView) myView.findViewById(R.id.profile_name);
            dis_gender=(TextView) myView.findViewById(R.id.gender);
            dis_faculty=(TextView) myView.findViewById(R.id.school);
            dis_age =(TextView) myView.findViewById(R.id.age);

            //on create display the information from the database using the retrieval from PHP
            SharedPreferences sh = this.getActivity().getSharedPreferences("preference", Context.MODE_PRIVATE);
//            username = sh.getString("username", "");
//            dis_username.setText(username);
            firstname = sh.getString("firstname", "");
            dis_firstname.setText(firstname);
            gender = sh.getString("gender", "");
            dis_gender.setText(gender);
            faculty = sh.getString("faculty", "");
            dis_faculty.setText(faculty);
            age = sh.getString("age", "");
            dis_age.setText(age);
            edit_Btn =(ImageButton) myView.findViewById(R.id.edit_button);

            edit_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editClicked();
                }
            });
        return myView;
    }
    public void editClicked(){

        startActivity(new Intent(getActivity(), ProfileEditActivity.class));
    }
}
