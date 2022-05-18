package com.example.duet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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
import com.example.duet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView profile_TXT_likes, profile_TXT_matches, profile_TXT_photos, profile_TXT_bio,email,name,birthdate,gender, interested,occupation;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViews();
        String email = getIntent().getStringExtra("email");
        getUserDetails(email);
        initNavigation();
    }

    private void findViews() {
        profile_TXT_likes = findViewById(R.id.profile_TXT_likes);
        profile_TXT_matches = findViewById(R.id.profile_TXT_matches);
        profile_TXT_photos = findViewById(R.id.profile_TXT_photos);
        profile_TXT_bio = findViewById(R.id.profile_TXT_bio);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.person);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        interested = findViewById(R.id.intrested);
        occupation = findViewById(R.id.occupation);

    }

    private void getUserDetails(String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/instances/search/byName/" + userEmail +"?userDomain=2022b.Yaeli.Bar.Gimelshtei&userEmail=avivit.yehezkel@s.afeka.ac.il";
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONArray respObj = new JSONArray(response);
                            JSONObject details = respObj.getJSONObject(0);
                            JSONObject attr = details.getJSONObject("instanceAttributes");
                            email.setText("Email: "+details.getString("name"));
                            name.setText("Name: "+attr.getString("firstname") +" "+ attr.getString("lastname"));
                            birthdate.setText("Birthdate: "+attr.getString("birthdate"));
                            gender.setText("Gender: "+attr.getString("gender"));
                            interested.setText("Interested In: "+attr.getString("interestedin"));
                            occupation.setText("Occupation: "+attr.getString("occupation"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ccc","profile "+error.toString());
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
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        //move to profile home
                        startActivity(new Intent(getApplicationContext(),MatchActivity.class));

                        return true;

                    case R.id.chats:
                        //move to chat activity
                        startActivity(new Intent(getApplicationContext(),ChatsActivity.class));

                        //overridePendingTransition(0,0);
                        return true;

                    case R.id.person:
                        //stay in this activity
                        //overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }



}