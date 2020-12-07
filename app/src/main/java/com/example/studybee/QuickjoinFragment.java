package com.example.studybee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.studybee.ui.InitAuthSDKActivity;
import com.example.studybee.ui.InitAuthSDKActivityJoin;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.awt.font.NumericShaper;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;
import static com.example.studybee.initsdk.AuthConstants.ip;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuickjoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickjoinFragment extends Fragment implements OnTaskCompleted, AuthConstants{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button joinButton;
    String msgType;
    String status;
    String room_description;
    String zoom_id;
    String zoom_pw;
    String time;
    String isTA;
    View rootView;

    int user_id;

    public static final String HOST = ip; //use your IP address

    // Set virtual directory of the host website
    public static final String DIR = "myproject";

    // Set request ID for all HTTP requests
    private static final String REQ_UPLOAD = "1003";

    public QuickjoinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickjoinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickjoinFragment newInstance(String param1, String param2) {
        QuickjoinFragment fragment = new QuickjoinFragment();
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
        rootView = inflater.inflate(R.layout.fragment_quickjoin, container, false);

        joinButton = (Button) rootView.findViewById(R.id.quickjoinButton);
        joinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                QuickJoinClicked();
            }
        });

        return rootView;
    }

    public void QuickJoinClicked()
    {
        msgType = REQ_UPLOAD;
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar c = Calendar.getInstance(tz);
        time =String.format("%04d" , c.get(Calendar.YEAR))+"-"+
                String.format("%02d" , c.get(Calendar.MONTH)+1)+"-"+
                String.format("%02d" , c.get(Calendar.DAY_OF_MONTH))+" "+
                String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE) + 10)+":"+
                String.format("%02d" , c.get(Calendar.SECOND));

        SharedPreferences prefs = this.getActivity().getSharedPreferences("preference", Context.MODE_PRIVATE);
        isTA = prefs.getString("identity","");
        user_id = prefs.getInt("id",0);

        String jsonString = convertToJSON();
        HttpAsyncTaskForHost task = new HttpAsyncTaskForHost(this);
        task.execute("http://"+HOST+"/"+DIR+"/quickJoin.php", jsonString);
    }

    public String convertToJSON() { //convert to JSON syntax?
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
            jsonText.key("type");
            jsonText.value(msgType);
            jsonText.key("start_time");
            jsonText.value(time);
            jsonText.key("isTA");
            jsonText.value(isTA);
            jsonText.key("user_id");
            jsonText.value(user_id);
            jsonText.endObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString(); //into JSON syntax
    }

    @Override
    public void onTaskCompleted(String response) {

        retrieveFromJSON(response);

        if (msgType.equals(REQ_UPLOAD) && status.equals("OK")){
            SharedPreferences prefs = this.getActivity().getSharedPreferences("preference", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("zoom_pw",zoom_pw);
            editor.putString("zoom_id",zoom_id);
            editor.putString("room_description",room_description);
            editor.commit();

            startActivity(new Intent(new Intent(getContext(), InitAuthSDKActivityJoin.class)));
        }
    }

    public void retrieveFromJSON(String message) {

        try {
            JSONObject jsonObject = new JSONObject(message);
            msgType = jsonObject.getString("type");
            if (msgType.equals(REQ_UPLOAD)) {
                status = jsonObject.getString("status");
                if (status.equals("OK")) {
//                    id = jsonObject.getInt("id");
                    room_description = jsonObject.getString("room_description");
                    zoom_id = jsonObject.getString("zoom_id");
                    zoom_pw = jsonObject.getString("zoom_pw");

                    //Toast.makeText(getApplicationContext(),"Room created successfully!",Toast.LENGTH_LONG).show();


                    //startActivity(new Intent(new Intent(getContext(), InitAuthSDKActivityJoin.class)));

//
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}