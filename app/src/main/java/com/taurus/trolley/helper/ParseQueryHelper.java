package com.taurus.trolley.helper;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.taurus.trolley.domain.Beacon;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.OfferHistory;
import com.taurus.trolley.domain.Shelf;
import com.taurus.trolley.domain.Shop;
import com.taurus.trolley.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semih on 14.11.2015.
 */
public class ParseQueryHelper {
    private static final String TAG = ParseQueryHelper.class.getSimpleName();
    public static final int OFFER_INDEX = 0;
    public static final int OFFER_HISTORY_INDEX = 0;
    private static final String CREATED_AT = "createdAt";
    private static final String MY_OFFER_HISTORY = "myOfferHistory";


    public static void getOfferHistory(final Callback callback) {
        final List results = new ArrayList();
        ParseQuery<OfferHistory> offerHistoryQuery = ParseQuery.getQuery(OfferHistory.class);
        offerHistoryQuery.include(generateIncludeParameter(OfferHistory.OFFER));
        offerHistoryQuery.whereEqualTo(OfferHistory.USER, User.currentUser());
        offerHistoryQuery.orderByDescending(CREATED_AT);
        offerHistoryQuery.findInBackground(new FindCallback<OfferHistory>() {
            @Override
            public void done(final List<OfferHistory> list, ParseException e) {
                if (e != null) {
                    callback.done(results, e);
                } else {
                    // Offers are found, save it for local data store
                    ParseObject.unpinAllInBackground(MY_OFFER_HISTORY, new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(MY_OFFER_HISTORY, list);
                        }
                    });
                    // invoke callback method
                    results.add(OFFER_HISTORY_INDEX, list);
                    callback.done(results, e);
                }
            }
        });

    }

    /**
     *
     * @param bluetoothAddress
     * @param callback
     */
    public static void getOfferFromBeacon(String bluetoothAddress, final Callback callback) {
        final List<ParseObject> results = new ArrayList();
        ParseQuery<Beacon> beaconQuery = ParseQuery.getQuery(com.taurus.trolley.domain.Beacon.class);
        beaconQuery.include(generateIncludeParameter(Beacon.SHELF, Shelf.SHOP, Shop.BRAND));
        beaconQuery.whereEqualTo(com.taurus.trolley.domain.Beacon.BLUETOOTH_ADDRESS, bluetoothAddress);
        beaconQuery.getFirstInBackground(new GetCallback<Beacon>() {
            @Override
            public void done(com.taurus.trolley.domain.Beacon beacon, ParseException e) {
                if (e != null) {
                    callback.done(results, e);
                } else {
                    // Beacon is found, now check offers for the shelf that related with this beacon
                    Shelf shelf = beacon.getShelf();
                    ParseQuery<Offer> offerQuery = ParseQuery.getQuery(Offer.class);
                    offerQuery.include(generateIncludeParameter(Offer.SHELF));
                    offerQuery.whereEqualTo(Offer.SHELF, shelf);
                    offerQuery.getFirstInBackground(new GetCallback<Offer>() {
                        @Override
                        public void done(Offer offer, ParseException e) {
                            if (e != null) {
                                callback.done(results, e);
                            } else {
                                results.add(OFFER_INDEX, offer);
                                callback.done(results, e);
                            }
                        }
                    });
                }

            }
        });
    }

    /**
     * @param includes : keys to generate (key1, key2, key3), sequence is important
     * @return a string like "key1.key2.key3"
     */

    private static String generateIncludeParameter(String... includes) {
        StringBuilder includeKey = new StringBuilder();

        for (String include : includes) {
            includeKey.append(include).append(".");
        }

        includeKey.deleteCharAt(includeKey.length() - 1);
        return includeKey.toString();
    }

    public interface Callback {
        void done(List results, ParseException e);
    }
}
