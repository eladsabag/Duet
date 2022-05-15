package com.example.duet.data;

public class UserDetails {
    String firstname;
    String lastname;
    String phone;
    String date;

    public String getFirstname() {
        return firstname;
    }

    public UserDetails setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserDetails setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDetails setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDate() {
        return date;
    }

    public UserDetails setDate(String date) {
        this.date = date;
        return this;
    }
}
