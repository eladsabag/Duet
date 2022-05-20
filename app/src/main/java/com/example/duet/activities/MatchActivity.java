package com.example.duet.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.MSP;
import com.example.duet.R;
import com.example.duet.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private MaterialTextView match_LBL_profile;
    String email;
    boolean isSpotify = false;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        findViews();
        //isSpotify = getIntent().getBooleanExtra("spotify",false);

        email = getIntent().getStringExtra("email");

//      getSpotifyUser();
        getUser();
        initNavigation();
    }

    private void findViews() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.home);
        match_LBL_profile= findViewById(R.id.match_LBL_profile);
        match_LBL_profile.setPaintFlags(match_LBL_profile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }


//    private void getSpotifyUser() {
//        String ENDPOINT = "https://api.spotify.com/v1/me";
//        RequestQueue mqueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
//            try {
//                email=response.getString("email");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                String token = MSP.getMe().getString("token","");
//                String auth = "Bearer " + token;
//                headers.put("Authorization", auth);
//                return headers;
//            }
//        };
//        mqueue.add(jsonObjectRequest);
//    }


    private void getUser() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/users/login/2022b.Yaeli.Bar.Gimelshtei/" + email;
        //Log.d("ccc","email = " + email);
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            JSONObject userId = respObj.getJSONObject("userId");
                            user.setEmail(userId.getString("email"));
                            user.setUsername(respObj.getString("username"));
                            user.setRole(respObj.getString("role"));
                            user.setAvatar(respObj.getString("avatar"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        queue.add(request);
    }


    private void initNavigation() {
        // Perform item selected listener

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        //stay in this activity
                        return true;

                    case R.id.chats:
                        //move to chat activity
                        startActivity(new Intent(getApplicationContext(),ChatsActivity.class));
                        //overridePendingTransition(0,0);
                        return true;

                    case R.id.person:
                        //move to profile activity
                        Intent profile = new Intent(getApplicationContext(),ProfileActivity.class);
                        profile.putExtra("email",email);
                        profile.putExtra("spotify",isSpotify);
                        startActivity(profile);
                        //overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }
}