package com.example.duet.data;

public class Song {
    private String song;
    private String artist;
    private String image;

    public Song() {
        this.song = "";
        this.artist = "";
        this.image = "";
    }

    public String getSong() {
        return song;
    }

    public Song setSong(String song) {
        this.song = song;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public Song setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Song setImage(String image) {
        this.image = image;
        return this;
    }

    @Override
    public String toString() {
        return song+" ,"+artist;
    }
}
