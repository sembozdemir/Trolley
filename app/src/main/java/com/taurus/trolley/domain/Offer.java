package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Offer {
    private String objectId;
    private Shop shop;
    private String description;
    private int value;

    public Offer() {
    }

    public Offer(Shop shop, String objectId, String description, int value) {
        this.shop = shop;
        this.objectId = objectId;
        this.description = description;
        this.value = value;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
