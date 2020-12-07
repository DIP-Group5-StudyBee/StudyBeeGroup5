package com.example.studybee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybee.adapter.UpcomingMeetingAdapter;
import com.example.studybee.model.UpcomingMeeting;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    private RecyclerView upcomingMeetingRv;
    private UpcomingMeetingAdapter upcomingMeetingAdapter;
    private CardView createButton, joinButton;
    private String isTA;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        upcomingMeetingRv = rootView.findViewById(R.id.upcoming_meetings_rv);

        //set up dummy data for model class for upcoming meetings
        List<UpcomingMeeting> upcomingMeetingList = new ArrayList<>();

        upcomingMeetingList.add(new UpcomingMeeting("Title 1", "Java Programming", "12hrs"));
        upcomingMeetingList.add(new UpcomingMeeting("Title 2", "Engine Comm", "12hrs"));
        upcomingMeetingList.add(new UpcomingMeeting("Title 3", "Alphabets", "12hrs"));

        setUpcomingRecycler(upcomingMeetingList);

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