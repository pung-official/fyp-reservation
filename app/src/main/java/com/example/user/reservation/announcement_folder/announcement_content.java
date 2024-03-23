package com.example.user.reservation.announcement_folder;

/**
 * Created by User on 07-Dec-17.
 */



public class announcement_content {

    private String announcement_date;
    private String announcement_description;
    private String announcement_title;


    public announcement_content(String announcement_date, String announcement_description, String announcement_title) {
        this.announcement_date = announcement_date;
        this.announcement_description = announcement_description;
        this.announcement_title = announcement_title;
    }

    public String getannouncement_date() {
        return announcement_date;
    }

    public String getannouncement_description() {
        return announcement_description;
    }

    public String getannouncement_title() {
        return announcement_title;
    }
}

