package com.example.user.reservation;


/**
 * Created by User on 14-Dec-17.
 */



public class BookingHistory_content {

     private String checkin;
     private String checkout;
     private String status;
     private String receipt;
    private String address;
    private String name;
    private int reservation_id;
    private int homestay_id;
    private double total_payment;

    public BookingHistory_content(int reservation_id, int homestay_id, String checkin, String checkout, double total_payment, String status, String receipt, String name, String address) {
        this.reservation_id = reservation_id;
        this.homestay_id = homestay_id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.total_payment = total_payment;
        this.status = status;
        this.receipt = receipt;
        this.name = name;
        this.address = address;
    }

    public double getTotal_payment() {
        return total_payment;
    }

    public int getHomestay_id() {
        return homestay_id;
    }


    public int getReservation_id() {
        return reservation_id;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getStatus() {
        return status;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}

