package com.example.duet.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class User {
    String email;
    String username;
    String avatar;
    String role;

    public User() {
        email="";
        username="";
        avatar="";
        role="PLAYER";
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public static class userJsonSerializer implements JsonSerializer<User> {

        @Override
        public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("email", context.serialize(user.getEmail()));
            object.add("role", context.serialize(user.getRole()));
            object.add("avatar", context.serialize(user.getAvatar()));
            object.add("username", context.serialize(user.getUsername()));
            return object;
        }

    }
}

