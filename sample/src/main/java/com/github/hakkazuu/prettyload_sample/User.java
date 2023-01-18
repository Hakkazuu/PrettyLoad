package com.github.hakkazuu.prettyload_sample;

public class User {

    private String mName;

    private String mCountry;

    private User() {}

    public User(String name, String country) {
        mName = name;
        mCountry = country;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

}