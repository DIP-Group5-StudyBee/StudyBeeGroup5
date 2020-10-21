package com.example.studybee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{

    View myView;
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
    public static final String HOST = "192.168.0.105"; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_DOWNLOAD = "1005";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_profile, container, false);
        dis_firstname=(TextView) myView.findViewById(R.id.full_name);
        dis_username=(TextView) myView.findViewById(R.id.profile_name);
        dis_gender=(TextView) myView.findViewById(R.id.gender);
        dis_faculty=(TextView) myView.findViewById(R.id.school);
        dis_age =(TextView) myView.findViewById(R.id.age);



        record_Btn=(Button) myView.findViewById(R.id.record_button);
        info_Btn=(Button) myView.findViewById(R.id.info_button);
        edit_Btn =(ImageButton) myView.findViewById(R.id.edit_button);
        fri_Btn = (Button)myView.findViewById(R.id.friend_button);

        //on create display the information from the database using the retrieval from PHP
        SharedPreferences sh = this.getActivity().getSharedPreferences("preference", Context.MODE_PRIVATE);
        username = sh.getString("username", "");
        dis_username.setText(username);
        firstname = sh.getString("firstname", "");
        dis_firstname.setText(firstname);
        gender = sh.getString("gender", "");
        dis_gender.setText(gender);
        faculty = sh.getString("faculty", "");
        dis_faculty.setText(faculty);
        age = sh.getString("age", "");
        dis_age.setText(age);


        edit_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editClicked();
            }
        });
        info_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                infotabClicked();
            }
        });
        record_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                recordtabClicked();
            }
        });
        fri_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                friendtabClicked();
            }
        });

        return myView;
    }

    public void editClicked(){
        startActivity(new Intent(getActivity(), ProfileEditActivity.class));
    }

    public void infotabClicked(){
        startActivity(new Intent(getActivity(), ProfileActivity_2.class));
    }
//
    public void recordtabClicked(){
        startActivity(new Intent(getActivity(), RecordsActivity.class));
    }
    public void friendtabClicked(){
        startActivity(new Intent(getActivity(), Display.class));
    }
//    @Override
//    public void onTaskCompleted(String response) {
//
//
//    }
}