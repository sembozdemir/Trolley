package com.taurus.trolley.dummy;

import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semih on 07.11.2015.
 */
public class DummyData {

    public static final String[] shoppingArray = {
            "Zara'dan %25 indirimden faydalandın. Süpersin.",
            "LCWaikiki'den 1 alana 1 bedave tshirt kampanyasından faydalandın. Çok şanslısın.",
            "DeFacto'dan %50 indirimden faydalandın. Süper."
    };

    public static List<Transaction> createTransactionList() {
        List<Transaction> list = new ArrayList<>();
        for(int i = 0; i < shoppingArray.length; i++) {
            Transaction transaction = new Transaction();
            Offer offer = new Offer();
            offer.setDescription(shoppingArray[i]);
            transaction.setOffer(offer);
            list.add(transaction);
        }

        return list;
    }
}
