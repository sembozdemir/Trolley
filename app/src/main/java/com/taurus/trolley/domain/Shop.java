package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("Shop")
public class Shop extends ParseObject{

    public static final String BRAND = "brand";
    public static final String GEO_POINT = "geoPoint";
    public static final String NAME = "name";

    public Shop() {
    }

    public Shop(String name, Brand brand, double latitude, double longitude) {
        setName(name);
        setBrand(brand);
        setGeoPoint(new ParseGeoPoint(latitude, longitude));
    }

    public Shop(String name, Brand brand, ParseGeoPoint geoPoint) {
        setName(name);
        setBrand(brand);
        setGeoPoint(geoPoint);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    private void setGeoPoint(ParseGeoPoint geoPoint) {
        put(GEO_POINT, geoPoint);
    }

    private void setGeoPoint(double latitude, double longitude) {
        put(GEO_POINT, new ParseGeoPoint(latitude, longitude));
    }

    public ParseGeoPoint getGeoPoint() {
        return getParseGeoPoint(GEO_POINT);
    }

    public Brand getBrand() {
        return (Brand) getParseObject(BRAND);
    }

    public void setBrand(Brand brand) {
        put(BRAND, brand);
    }
}
