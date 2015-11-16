package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("_User")
public class User extends ParseUser {
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String GENDER = "gender";
    public static final String BIRTHDAY = "birthDay";
    public static final String SCORE = "score";
    public static final String BADGES = "badges";

    public User() {
    }

    public User(String firstName, String lastName, String gender, Date birthDay, int score) {
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setBirthDay(birthDay);
        setScore(score);
        setBadges(new ArrayList<Badge>());
    }

    public String getFirstName() {
        return getString(FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        put(FIRST_NAME, firstName);
    }

    public String getLastName() {
        return getString(LAST_NAME);
    }

    public void setLastName(String lastName) {
        put(LAST_NAME, lastName);
    }

    public String getGender() {
        return getString(GENDER);
    }

    public void setGender(String gender) {
        put(GENDER, gender);
    }

    public Date getBirthDay() {
        return getDate(BIRTHDAY);
    }

    public void setBirthDay(Date birthDay) {
        put(BIRTHDAY, birthDay);
    }

    public int getScore() {
        return getInt(SCORE);
    }

    public void setScore(int score) {
        put(SCORE, score);
    }

    public List<Badge> getBadges() {
        return getList(BADGES);
    }

    public void setBadges(List<Badge> badges) {
        put(BADGES, badges);
    }

    public static User currentUser() {
        //return ParseUser.createWithoutData(User.class, ParseUser.getCurrentUser().getObjectId());
        return (User) ParseUser.getCurrentUser();
    }
}
