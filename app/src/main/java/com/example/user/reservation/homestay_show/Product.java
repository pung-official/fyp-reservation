package com.example.user.reservation.homestay_show;

/**
 * Created by User on 01-Dec-17.
 */


public class Product {
    private int id;
    private int homestay_postcode;
    private int homestay_capacity;
    private int homestay_room;
    private String homestay_name;
    private String homestay_address;
    private String homestay_state;
    private String homestay_city;
    private String homestay_status;
    private String homestay_description;
    private double homestay_price_perday;
    private String image_name;

    public Product(int id, String homestay_name, String homestay_address, int homestay_postcode,
                   String homestay_state, String homestay_city, int homestay_capacity, int homestay_room,
                   String homestay_status, double homestay_price_perday, String homestay_description, String image_name) {
        this.id = id;
        this.homestay_name = homestay_name;
        this.homestay_address = homestay_address;
        this.homestay_postcode = homestay_postcode;
        this.homestay_state = homestay_state;
        this.homestay_city = homestay_city;
        this.homestay_capacity = homestay_capacity;
        this.homestay_room = homestay_room;
        this.homestay_status = homestay_status;
        this.homestay_price_perday = homestay_price_perday;
        this.homestay_description = homestay_description;
        this.image_name = image_name;

    }

    public int getId() {
        return id;
    }


    public int getHomestay_postcode() {
        return homestay_postcode;
    }

    public int getHomestay_capacity() {
        return homestay_capacity;
    }

    public int getHomestay_room() {
        return homestay_room;
    }

    public String getHomestay_name() {
        return homestay_name;
    }

    public String getHomestay_address() {
        return homestay_address;
    }

    public String getHomestay_state() {
        return homestay_state;
    }

    public String getHomestay_city() {
        return homestay_city;
    }

    public String getHomestay_status() {
        return homestay_status;
    }

    public String getHomestay_description() {
        return homestay_description;
    }



    public String getImage_name() {
       // return image_name;
        return ("http://istay.000webhostapp.com/owner/"+image_name);
    }

    public double getHomestay_price_perday() {
        return homestay_price_perday;
    }
}