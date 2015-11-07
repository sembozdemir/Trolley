package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class Badge {
    private String objectId;
    private int iconResId;

    public Badge() {
    }

    public Badge(String objectId, int iconResId) {
        this.objectId = objectId;
        this.iconResId = iconResId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
