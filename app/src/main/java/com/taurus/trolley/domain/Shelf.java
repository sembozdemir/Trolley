package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Shelf {
    private String objectId;
    private Shop shop;
    private String name;

    public Shelf() {
    }

    public Shelf(String objectId, Shop shop, String name) {
        this.objectId = objectId;
        this.shop = shop;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
