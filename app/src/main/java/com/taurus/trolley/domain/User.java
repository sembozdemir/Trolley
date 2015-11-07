package com.taurus.trolley.domain;

import java.util.Date;

/**
 * Created by semih on 07.11.2015.
 */
public class User {
    private String objectId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private int score;

    public User() {
    }

    public User(String objectId, String firstName, String lastName, String gender, Date birthDate, int score) {
        this.objectId = objectId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.score = score;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
