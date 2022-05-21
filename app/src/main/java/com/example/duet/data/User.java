package com.example.duet.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.duet.retrofit.RetrofitClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public User setAvatar() {
        // TODO - get user gender male or female...
        Call<ResponseBody> call = RetrofitClient.getInstance().getMyApi().getGender("male/"+username+".png");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        avatar = bmp.toString();
//                        imageView.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) { }
        });
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                '}';
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

