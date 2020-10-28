package com.example.studybee.model;

public class UpcomingMeeting {

    String host_name;
    String room_name;
    String room_description;

    public UpcomingMeeting(String host_name, String room_name, String room_description) {
        this.host_name = host_name;
        this.room_name = room_name;
        this.room_description = room_description;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_description() {
        return room_description;
    }

    public void setRoom_description(String room_description) {
        this.room_description = room_description;
    }
}