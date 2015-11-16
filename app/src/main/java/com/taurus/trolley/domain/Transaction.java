package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 * @deprecated no needed anymore
 */

@ParseClassName("Transaction")
public class Transaction extends ParseObject {
    public static final String USER = "user";
    public static final String OFFER = "offer";

    public Transaction() {
    }

    public Transaction(User user, Offer offer) {
        setUser(user);
        setOffer(offer);
    }

    public User getUser() {
        return (User) getParseUser(USER);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public Offer getOffer() {
        return (Offer) getParseObject(OFFER);
    }

    public void setOffer(Offer offer) {
        put(OFFER, offer);
    }
}
