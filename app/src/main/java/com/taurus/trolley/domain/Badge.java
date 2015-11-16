package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("Badge")
public class Badge extends ParseObject {
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";

    public Badge() {
    }

    public Badge(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }
}
