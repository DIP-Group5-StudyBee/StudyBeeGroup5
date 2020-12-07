package com.example.studybee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static com.example.studybee.initsdk.AuthConstants.ip;


public class ProfileFriendFragment extends Fragment implements AuthConstants{
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

    public ProfileFriendFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friend, container, false);

        et_firstName = (EditText) v.findViewById(R.id.editTextTextPersonName);
        SButton = v.findViewById(R.id.button);

        NButton = v.findViewById(R.id.imageButtonA);//link to basic details page
        NButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[0].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(getActivity(), basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[0]).putExtra("facu",tfacu[0]).putExtra("email",tmail[0]);
                startActivity(intent);
//                finish();
            }
        });
        NButton1 = v.findViewById(R.id.imageButtonB);//link to basic details page
        NButton1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[1].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(getActivity(),basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[1]).putExtra("facu",tfacu[1]).putExtra("email",tmail[1]);
                startActivity(intent);
//                finish();
            }
        });
        NButton2 = v.findViewById(R.id.imageButtonC);//link to basic details page
        NButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String data = textViews[2].getText().toString();
                String tname[] = firstname.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tmail [] = email.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                String tfacu [] = faculty.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").replaceAll("\"","").split(",");
                Intent intent = new Intent(getActivity(),basicDetails.class);
                intent.putExtra("user",data).putExtra("name",tname[2]).putExtra("facu",tfacu[2]).putExtra("email",tmail[2]);
                startActivity(intent);
//                finish();
            }
        });

        return v;
    }

}