package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("Offer")
public class Offer extends ParseObject {

    public static final String SHELF = "shelf";
    public static final String DESCRIPTION = "description";
    public static final String SCORE_VALUE = "scoreValue";
    public static final String OFFER_IMAGE_URL = "offerImageUrl";

    public Offer() {
    }

    public Offer(Shelf shelf, String description, int scoreValue, String imageUrl) {
        setShelf(shelf);
        setDescription(description);
        setScoreValue(scoreValue);
        setOfferImageUrl(imageUrl);
    }

    public String getOfferImageUrl() {
        return getString(OFFER_IMAGE_URL);
    }

    public void setOfferImageUrl(String imageUrl) {
        put(OFFER_IMAGE_URL, imageUrl);
    }

    public Shelf getShelf() {
        return (Shelf) getParseObject(SHELF);
    }

    public void setShelf(Shelf shelf) {
        put(SHELF, shelf);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public int getScoreValue() {
        return getInt(SCORE_VALUE);
    }

    public void setScoreValue(int scoreValue) {
        put(SCORE_VALUE, scoreValue);
    }
}
