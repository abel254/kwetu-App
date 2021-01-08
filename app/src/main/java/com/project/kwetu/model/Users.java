package com.project.kwetu.model;

public class Users {

    String tenant_name;
    String email;
    String house_number;
    String phone_number;
    String password;
    String image;
    String uid;

    public Users() {
    }

    public Users(String tenant_name, String email, String house_number, String phone_number, String password) {
        this.tenant_name = tenant_name;
        this.email = email;
        this.house_number = house_number;
        this.phone_number = phone_number;
        this.password = password;
        this.image = image;
        this.uid = uid;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
