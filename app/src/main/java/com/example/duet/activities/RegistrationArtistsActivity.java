package com.example.duet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RegistrationArtistsActivity extends AppCompatActivity {
    private TextInputEditText artists_EDT_first, artists_EDT_second, artists_EDT_third;
    private String[] chosenArtists;
    private MaterialButton artists_BTN_next;
    private MaterialTextView artists_LBL_error;
    private String[] userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_artists);

        chosenArtists = new String[3];

        findViews();

        initViews();
    }

    private void initViews() {
        artists_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(artists_EDT_first.getText().toString().length() != 0 &&  artists_EDT_second.getText().toString().length() != 0 && artists_EDT_third.getText().toString().length() != 0) {
                    // Set artists array
                    chosenArtists[0]= artists_EDT_first.getText().toString();
                    chosenArtists[1]= artists_EDT_second.getText().toString();
                    chosenArtists[2]= artists_EDT_third.getText().toString();

                    // this is data from last activity - RegistrationMainActivity
                    userDetails = getIntent().getStringArrayExtra("userDetails");

                    // send all data to next page
                    Intent intent = new Intent(RegistrationArtistsActivity.this, RegistrationSongsActivity.class);
                    intent.putExtra("userDetails",userDetails);
                    intent.putExtra("chosenArtists",chosenArtists);
                    startActivity(intent);
                } else {
                    artists_LBL_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void findViews() {
        artists_EDT_first = findViewById(R.id.artists_EDT_first);
        artists_EDT_second = findViewById(R.id.artists_EDT_second);
        artists_EDT_third = findViewById(R.id.artists_EDT_third);
        artists_LBL_error = findViewById(R.id.artists_LBL_error);
        artists_BTN_next = findViewById(R.id.artists_BTN_next);
    }
}