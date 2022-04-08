package com.example.duet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RegistrationArtistsActivity extends AppCompatActivity {
    // fake data - random top 10 artists
    private String[] artists = {"Drake","Maluma","Ariana Grande","Beyonce","Kendrick Lemar","Arctic Monkey's","Bad Bunny","Ravid Plotink","Noa Kirel","Omer Adam"};

    private ImageView[] artists_IMG;
    private CheckBox[] artists_BOX;

    private ArrayList<String> chosenArtists;
    private MaterialButton artists_BTN_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_artists);
        artists_IMG = new ImageView[]{findViewById(R.id.artists_IMG_artist0), findViewById(R.id.artists_IMG_artist1), findViewById(R.id.artists_IMG_artist2), findViewById(R.id.artists_IMG_artist3), findViewById(R.id.artists_IMG_artist4), findViewById(R.id.artists_IMG_artist5), findViewById(R.id.artists_IMG_artist6), findViewById(R.id.artists_IMG_artist7), findViewById(R.id.artists_IMG_artist8),findViewById(R.id.artists_IMG_artist9)};
        artists_BOX = new CheckBox[]{findViewById(R.id.artists_BOX_artist0), findViewById(R.id.artists_BOX_artist1), findViewById(R.id.artists_BOX_artist2), findViewById(R.id.artists_BOX_artist3), findViewById(R.id.artists_BOX_artist4), findViewById(R.id.artists_BOX_artist5), findViewById(R.id.artists_BOX_artist6), findViewById(R.id.artists_BOX_artist7), findViewById(R.id.artists_BOX_artist8),findViewById(R.id.artists_BOX_artist9)};
        chosenArtists = new ArrayList<>();
        setCheckBoxNames();

        artists_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO -  this button should be disabled if the string array(artists chosen) is smaller than 3

                // this is data from last activiy - RegistrationMainActivity
                Intent i = getIntent();
                String[] userDetails = i.getStringArrayExtra("userDetails");

                Intent intent = new Intent(RegistrationArtistsActivity.this, RegistrationSongsActivity.class);

                // send all data to next page
                intent.putExtra("userDetails",userDetails);
                intent.putExtra("chosenArtists",chosenArtists);

                startActivity(intent);
                finish();
            }
        });
    }

    private void setCheckBoxNames() {
        // TODO - Replace fake data with data from GET REQUEST to Spotify API for the top 10 artists worldwide right now.

        // always 10 artists
        for (int i = 0; i < 10; i++)
            artists_BOX[i].setText(artists[i]);
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked and add it to the array
        // TODO - pop a message that only 3 artists can be chosen and disable other selection when 3 checkboxes clicked
        // TODO - remove artists from list if user uncheck the box
        for (int i = 0; i < 10; i++) {
            if (checked && chosenArtists.size() < 3 && !chosenArtists.contains(artists_BOX[i].getText().toString()))
                chosenArtists.add(artists_BOX[i].getText().toString());
        }
    }
}