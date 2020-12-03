package com.example.studybee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        //inflating new upcoming meeting card item
        View view = LayoutInflater.from(context).inflate(R.layout.new_upcoming_meeting_item, parent, false);
        return new UpcomingMeetingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMeetingViewHolder holder, int position) {

        holder.title.setText(upcomingMeetingList.get(position).getTitle());
        holder.topic.setText(upcomingMeetingList.get(position).getTopic());
        holder.time.setText(upcomingMeetingList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return upcomingMeetingList.size();
    }

    public static final class UpcomingMeetingViewHolder extends RecyclerView.ViewHolder {

        TextView title, topic, time;

        public UpcomingMeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            //initializing the widgets by id in the new upcoming meeting card item
            title = itemView.findViewById(R.id.title);
            topic = itemView.findViewById(R.id.topic_value);
            time = itemView.findViewById(R.id.time_txt);


        }
    }

}


