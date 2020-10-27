package com.example.studybee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybee.R;
import com.example.studybee.model.UpcomingMeeting;

import java.util.List;

public class UpcomingMeetingAdapter extends RecyclerView.Adapter<UpcomingMeetingAdapter.UpcomingMeetingViewHolder> {

    Context context;
    List<UpcomingMeeting> upcomingMeetingList;

    public UpcomingMeetingAdapter(Context context, List<UpcomingMeeting> upcomingMeetingList) {
        this.context = context;
        this.upcomingMeetingList = upcomingMeetingList;
    }

    @NonNull
    @Override
    public UpcomingMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_meeting_row_item, parent, false);
        return new UpcomingMeetingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMeetingViewHolder holder, int position) {

        holder.host_name.setText(upcomingMeetingList.get(position).getHost_name());
        holder.room_name.setText(upcomingMeetingList.get(position).getRoom_name());
        holder.room_description.setText(upcomingMeetingList.get(position).getRoom_description());

    }

    @Override
    public int getItemCount() {
        return upcomingMeetingList.size();
    }

    public static final class UpcomingMeetingViewHolder extends RecyclerView.ViewHolder{

        TextView host_name, room_name, room_description;

        public UpcomingMeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            host_name = itemView.findViewById(R.id.host_name);
            room_name = itemView.findViewById(R.id.room_name);
            room_description = itemView.findViewById(R.id.room_description);


        }
    }

}


