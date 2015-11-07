package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Transaction {
    private String objectId;
    private User user;
    private Offer offer;

    public Transaction() {
    }

    public Transaction(String objectId, User user, Offer offer) {
        this.objectId = objectId;
        this.user = user;
        this.offer = offer;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
