package com.taurus.trolley.busevents;

import com.taurus.trolley.domain.OfferHistory;

/**
 * Created by semih on 14.11.2015.
 */
public class NewOfferEvent {
    public OfferHistory offerHistory;

    public NewOfferEvent(OfferHistory offerHistory) {
        this.offerHistory = offerHistory;
    }
}
