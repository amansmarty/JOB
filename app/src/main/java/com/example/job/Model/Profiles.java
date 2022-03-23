package com.example.job.Model;

public class Profiles {
    String first_name,last_name,profile;

    public Profiles(String first_name, String last_name, String profile) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile = profile;
    }

    public Profiles() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
