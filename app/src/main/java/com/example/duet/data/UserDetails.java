package com.example.duet.data;

public class UserDetails {
    String firstname;
    String lastname;
    String age;
    String occupation;
    String date;
    String gender;
    String interested;
    String artists;
    String song;

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

    public String getAge() {
        return age;
    }

    public UserDetails setAge(String age) {
        this.age = age;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public UserDetails setOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public String getDate() {
        return date;
    }

    public UserDetails setDate(String date) {
        this.date = date;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserDetails setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getInterested() {
        return interested;
    }

    public UserDetails setInterested(String interested) {
        this.interested = interested;
        return this;
    }

    public String getArtists() {
        return artists;
    }

    public UserDetails setArtists(String artists) {
        this.artists = artists;
        return this;
    }

    public String getSong() {
        return song;
    }

    public UserDetails setSong(String song) {
        this.song = song;
        return this;
    }
}
