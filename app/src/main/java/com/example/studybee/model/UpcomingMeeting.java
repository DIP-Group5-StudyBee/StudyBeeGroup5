package com.example.studybee.model;

public class UpcomingMeeting {

    String title;
    String topic;
    String time;

    public UpcomingMeeting(String title, String topic, String time) {
        this.title = title;
        this.topic = topic;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }


    public String getTopic() {
        return topic;
    }


    public String getTime() {
        return time;
    }
}