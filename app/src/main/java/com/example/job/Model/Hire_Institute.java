package com.example.job.Model;

public class Hire_Institute {
    String email,password;
    String Institute_Name,Address,City,State;
    String nirf,social,about;
    String work_fl;

    public Hire_Institute(String work_fl) {
        this.work_fl = work_fl;
    }

    public Hire_Institute(String institute_Name, String address, String city, String state) {
        Institute_Name = institute_Name;
        Address = address;
        City = city;
        State = state;
    }

    public Hire_Institute(String nirf, String social, String about) {
        this.nirf = nirf;
        this.social = social;
        this.about = about;
    }

    public Hire_Institute(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Hire_Institute() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstitute_Name() {
        return Institute_Name;
    }

    public void setInstitute_Name(String institute_Name) {
        Institute_Name = institute_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getNirf() {
        return nirf;
    }

    public void setNirf(String nirf) {
        this.nirf = nirf;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWork_fl() {
        return work_fl;
    }

    public void setWork_fl(String work_fl) {
        this.work_fl = work_fl;
    }
}
