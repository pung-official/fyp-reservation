package com.example.user.reservation;

/**
 * Created by User on 27-Dec-17.
 */
public class rating_content {

    private String rating;
    private String comment;

    public rating_content(String rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }


    public String getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
