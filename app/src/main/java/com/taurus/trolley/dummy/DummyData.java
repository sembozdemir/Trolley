package com.taurus.trolley.dummy;

import com.taurus.trolley.domain.Beacon;
import com.taurus.trolley.domain.Brand;
import com.taurus.trolley.domain.Offer;
import com.taurus.trolley.domain.Shelf;
import com.taurus.trolley.domain.Shop;
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

    public static void setDummiesForParse() {
        // Create Brand
        Brand zara = new Brand("Zara", "http://imgreview.com/i/gVL2w");
        zara.saveInBackground();
        // create Shop
        Shop zaraForumBornova = new Shop("Zara Forum Bornova", zara, 42.0, 44.0);
        zaraForumBornova.saveInBackground();
        // create Jeans Shelf
        Shelf zaraShopJeansShelf = new Shelf("Zara Forum Bornova Shelf Jeans", zaraForumBornova);
        zaraShopJeansShelf.saveInBackground();
        // create Shirts Shelf
        Shelf zaraShopShirtsShelf = new Shelf("Zara Forum Bornova Shelf Shirts", zaraForumBornova);
        zaraShopJeansShelf.saveInBackground();
        // create Offer for Jeans
        Offer offerJeans = new Offer(zaraShopJeansShelf, "Tebrikler, Zara'dan bütün jeans ürünlerinde %50 indirim fırsatı yakaladın! Keşfetmeye devam et!", 5, "http://imgreview.com/i/gVL2w");
        offerJeans.saveInBackground();
        // create Offer for Shirts
        Offer offerShirts = new Offer(zaraShopShirtsShelf, "Şimdi, sadece sana özel! Zara'dan 1 gömlek alırsan ikincisi anında hediye! Bu fırsat kaçmaz!", 5, "http://imgreview.com/i/gVL2w");
        offerShirts.saveInBackground();
        // create Beacon for Jeans shelf
        Beacon beaconJeans = new Beacon("E2:93:2A:92:EE:80", zaraShopJeansShelf);
        beaconJeans.saveInBackground();
        // create Beacon for Shirts shelf
        Beacon beaconShirts = new Beacon("D7:53:00:31:7D:1D", zaraShopShirtsShelf);
        beaconShirts.saveInBackground();
    }

    public static void addNewShops() {
        // create brand
        Brand lcw = new Brand("LCW", "http://imgreview.com/gVMo8");
        lcw.saveInBackground();
        Shop lcwOptimum = new Shop("LCW Optimum", lcw, 38.3386898, 27.1345174);
        lcwOptimum.saveInBackground();
        Shop lcwUcyol = new Shop("LCW Ucyol", lcw, 38.4049643, 27.126327);
        lcwUcyol.saveInBackground();
    }
}
