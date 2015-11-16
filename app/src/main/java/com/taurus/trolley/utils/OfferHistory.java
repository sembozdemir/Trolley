package com.taurus.trolley.utils;

import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semih on 14.11.2015.
 */
public class OfferHistory {
    private static final int CAPACITY = 20;
    private static final String KEY_OFFER_HISTORY = "offerHistory";

    private static OfferHistory instance = new OfferHistory();

    public List<Offer> offerHistory;

    private OfferHistory() {

    }

    public static void init() {
        instance = new OfferHistory();
        instance.offerHistory = instance.geListFromParse();
    }

    private List<Offer> geListFromParse() {
        if (User.currentUser().has(KEY_OFFER_HISTORY)) {
            return User.getCurrentUser().getList(KEY_OFFER_HISTORY);
        }
        return new ArrayList<>();
    }

    public static OfferHistory getInstance() {
        return instance;
    }

    public static void save() {
        User.currentUser().addAll(KEY_OFFER_HISTORY, instance.offerHistory);
        User.currentUser().saveInBackground();
    }

    public static List<Offer> getList() {
        return instance.offerHistory;
    }

    public static boolean hasOffer(Offer item) {
        return instance.offerHistory.contains(item);
    }

    public static void add(Offer item) {
        instance.offerHistory.add(0, item);
        save();
    }
}
