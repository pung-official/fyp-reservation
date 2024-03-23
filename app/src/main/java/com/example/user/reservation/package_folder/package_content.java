package com.example.user.reservation.package_folder;

/**
 * Created by User on 09-Dec-17.
 */
public class package_content {

    private String package_name;
    private double package_price;
    private String package_description;
    private String package_day;
    private String package_address;

    public package_content( String package_name, double package_price, String package_description,
                   String package_day,String package_address) {

        this.package_name = package_name;
        this.package_price = package_price;
        this.package_description = package_description;
        this.package_day = package_day;
        this.package_address = package_address;

    }

    public String getPackage_name() {
        return package_name;
    }

    public double getPackage_price() {
        return package_price;
    }

    public String getPackage_description() {
        return package_description;
    }

    public String getPackage_day() {
        return package_day;
    }

    public String getPackage_address() {
        return package_address;
    }
}