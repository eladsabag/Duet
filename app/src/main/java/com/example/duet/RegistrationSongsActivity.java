package com.example.duet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RegistrationSongsActivity extends AppCompatActivity {

    private TextView songs_LBL_trackName;
    private ImageView songs_IMG_image;
    private TextView songs_LBL_artistName;
    private TextView text;
    private CheckBox checkBox;
    private LinearLayout ll1;
    private MaterialButton songs_BTN_finish;
    private ArrayList<String> chosenSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_songs);
        songs_LBL_trackName = findViewById(R.id.songs_LBL_trackName);
        songs_LBL_artistName = findViewById(R.id.songs_LBL_artistName);
        checkBox = findViewById(R.id.checkBox);
        ll1 = findViewById(R.id.ll1);
        songs_IMG_image = findViewById(R.id.songs_IMG_image);
        String url = "https://i.scdn.co/image/ab67616d0000b2739a508628257cebd4539116cc";
        String trackName = "Envolver";
        String artistName = "Anitta";
        songs_LBL_trackName.setText(trackName);
        songs_LBL_artistName.setText(artistName);
        SetImage(songs_IMG_image,url);

        checkBox = findViewById(R.id.checkBox);
        chosenSongs = new ArrayList<>();
        songs_BTN_finish = findViewById(R.id.songs_BTN_finish);

        //TODO: linear layout array
        //TODO: see what one json brings

        // TODO -  GET REQUEST to Spotify API for the top 10 songs worldwide right now.

        songs_BTN_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO -  this button should be disabled if the string array(songs chosen) is smaller than 3

                // this is data from last activiy - RegistrationMainActivity
                Intent i = getIntent();
                String[] userDetails = i.getStringArrayExtra("userDetails");
                String[] chosenArtists = i.getStringArrayExtra("chosenArtists");

                // TODO - POST REQUEST to backend server in order to sign the user with the details - userDetails, chosenArtists, chosenSongs.


                // TODO - in here we will need to direct the user to the app page with all the matches and profiles, but only if the user finished registration.

//                Intent intent = new Intent(RegistrationSongsActivity.this, AppPage.class);
//                startActivity(intent);
//                finish();
            }
        });

    }

    private void SetImage(ImageView img, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                img.setImageResource(R.drawable.ic_launcher_background);
            }
        });
        requestQueue.add(request);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    ll1.setBackgroundColor(Color.parseColor("#5C9A4141"));
                    chosenSongs.add(checkBox.getText().toString());
                }
                else {
                    ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    chosenSongs.remove(checkBox.getText().toString());
                }
                break;
        }
    }
}