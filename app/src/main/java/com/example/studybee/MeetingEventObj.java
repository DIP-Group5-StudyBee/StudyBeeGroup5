package com.example.studybee;

public class MeetingEventObj {
    public String meeting_name;
    public String group_size;
    public String start_time;
    public String room_description;
    public String host_name;
    public String zoom_id;
    public String zoom_pw;
    public String meeting_id;
//, String meeting_id
    public MeetingEventObj(String meeting_name, String group_size, String start_time, String room_description, String host_name, String zoom_id, String zoom_pw){
        this.meeting_name = meeting_name;
        this.group_size = group_size;
        this.start_time = start_time;
        this.room_description = room_description;
        this.host_name = host_name;
        this.zoom_id = zoom_id;
        this.zoom_pw = zoom_pw;
//        this.meeting_id = meeting_id;
    }
}
