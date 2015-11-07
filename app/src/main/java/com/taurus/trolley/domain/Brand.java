package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Brand {
    private String objectId;
    private String name;
    private String logoUrl;

    public Brand() {
    }

    public Brand(String objectId, String name, String logoUrl) {
        this.objectId = objectId;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
