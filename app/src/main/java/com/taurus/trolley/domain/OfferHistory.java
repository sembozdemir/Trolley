package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 16.11.2015.
 */
@ParseClassName("OfferHistory")
public class OfferHistory extends ParseObject {
    public static final String OFFER = "offer";
    public static final String USER = "user";
    public static final String IS_TAKEN = "isTaken";

    public OfferHistory() {
    }

    public OfferHistory(Offer offer, User user, boolean isTaken) {
        setOffer(offer);
        setUser(user);
        setTaken(isTaken);
    }

    public Offer getOffer() {
        return (Offer) getParseObject(OFFER);
    }

    public void setOffer(Offer offer) {
        put(OFFER, offer);
    }

    public User getUser() {
        return (User) getParseUser(USER);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public boolean isTaken() {
        return getBoolean(IS_TAKEN);
    }

    public void setTaken(boolean isTaken) {
        put(IS_TAKEN, isTaken);
    }
}
