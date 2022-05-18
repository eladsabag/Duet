package com.example.duet.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textview.MaterialTextView;

public class MatchActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private MaterialTextView match_LBL_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.home);
        match_LBL_profile= findViewById(R.id.match_LBL_profile);

        match_LBL_profile.setPaintFlags(match_LBL_profile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        initNavigation();



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
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        //overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }
}