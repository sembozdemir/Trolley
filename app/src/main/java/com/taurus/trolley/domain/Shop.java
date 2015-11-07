package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Shop {
    private String objectId;
    private Brand brand;
    double latitude;
    double longitude;

    public Shop() {
    }

    public Shop(String objectId, Brand brand, double latitude, double longitude) {
        this.objectId = objectId;
        this.brand = brand;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
