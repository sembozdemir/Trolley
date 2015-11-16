package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("Shelf")
public class Shelf extends ParseObject {

    public static final String SHOP = "shop";
    public static final String NAME = "name";

    public Shelf() {
    }

    public Shelf(String name, Shop shop) {
        setShop(shop);
        setName(name);
    }

    public Shop getShop() {
        return (Shop) getParseObject(SHOP);
    }

    public void setShop(Shop shop) {
        put(SHOP, shop);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }
}
