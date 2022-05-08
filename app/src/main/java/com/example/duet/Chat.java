package com.example.duet;

public class Chat {
    private String name;
    private String lastMessage;
    private String image;
    private boolean selected = false;

    public Chat() {
        this.name = "name";
        this.lastMessage = "lastMessage";
        this.image = "ic_spotify";
    }

    public String getName() {
        return name;
    }

    public Chat setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Chat setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Chat setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public Chat setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }
}
