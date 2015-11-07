package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Beacon {
    private String objectId;
    private String beaconKey;
    private Shelf shelf;

    public Beacon() {
    }

    public Beacon(String objectId, String beaconKey, Shelf shelf) {
        this.objectId = objectId;
        this.beaconKey = beaconKey;
        this.shelf = shelf;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getBeaconKey() {
        return beaconKey;
    }

    public void setBeaconKey(String beaconKey) {
        this.beaconKey = beaconKey;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
