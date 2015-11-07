package com.taurus.trolley.domain;

/**
 * Created by semih on 07.11.2015.
 */
public class UserBadge {
    private String objectId;
    private User user;
    private Badge badge;

    public UserBadge() {
    }

    public UserBadge(String objectId, User user, Badge badge) {
        this.objectId = objectId;
        this.user = user;
        this.badge = badge;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
}
