package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */
@ParseClassName("Brand")
public class Brand extends ParseObject {

    public static final String NAME = "name";
    public static final String LOGO_URL = "logoUrl";

    public Brand() {
    }

    public Brand(String name, String logoUrl) {
        setName(name);
        setLogoUrl(logoUrl);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getLogoUrl() {
        return getString(LOGO_URL);
    }

    public void setLogoUrl(String logoUrl) {
        put(LOGO_URL, logoUrl);
    }
}
