package com.example.duet.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duet.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView profile_TXT_likes, profile_TXT_matches, profile_TXT_photos, profile_TXT_interests, profile_TXT_bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
    }

    private void findViews() {
        profile_TXT_likes = findViewById(R.id.profile_TXT_likes);
        profile_TXT_matches = findViewById(R.id.profile_TXT_matches);
        profile_TXT_photos = findViewById(R.id.profile_TXT_photos);
        profile_TXT_interests = findViewById(R.id.profile_TXT_interests);
        profile_TXT_bio = findViewById(R.id.profile_TXT_bio);
    }
}