package com.example.duet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {
    private TextView profile_TXT_likes, profile_TXT_matches, profile_TXT_photos, profile_TXT_interests, profile_TXT_bio;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViews();
        initNavigation();
    }

    private void findViews() {
        profile_TXT_likes = findViewById(R.id.profile_TXT_likes);
        profile_TXT_matches = findViewById(R.id.profile_TXT_matches);
        profile_TXT_photos = findViewById(R.id.profile_TXT_photos);
        profile_TXT_interests = findViewById(R.id.profile_TXT_interests);
        profile_TXT_bio = findViewById(R.id.profile_TXT_bio);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.person);
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