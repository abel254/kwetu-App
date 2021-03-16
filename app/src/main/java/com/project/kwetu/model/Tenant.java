package com.project.kwetu.model;

public class Tenant {

    String name;
    String email;
    String houseNumber;
    String phoneNumber;
    String idNumber;
    String password;


    public Tenant() {
    }

    public Tenant(String name, String email, String houseNumber, String phoneNumber, String idNumber, String password) {
        this.name = name;
        this.email = email;
        this.houseNumber = houseNumber;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
