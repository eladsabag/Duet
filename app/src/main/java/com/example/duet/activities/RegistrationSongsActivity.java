package com.example.duet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.R;
import com.example.duet.data.Song;
import com.example.duet.SongAdapter;
import com.example.duet.data.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RegistrationSongsActivity extends AppCompatActivity {

    private TextView songs_LBL_trackName;
    private ImageView songs_IMG_image;
    private TextView songs_LBL_artistName;
    private TextView text;
    //private CheckBox checkBox;
    private MaterialButton songs_BTN_finish;
    private ArrayList<String> chosenSongs;
    private RecyclerView songs_LST_songs;
    private User user;
    private Song song;
    private String jsonBody;
    private String jsonDetails;
    private String []userDetails;
    private String[] chosenArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_songs);
        userDetails = getIntent().getStringArrayExtra("userDetails");
        chosenArtists = getIntent().getStringArrayExtra("chosenArtists");
        findViews();
        makeSong();
        makeUser();




        // TODO -  GET REQUEST to Spotify API for the top 10 songs worldwide right now.

        songs_BTN_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO -  this button should be disabled if the string array(songs chosen) is smaller than 1

                // this is data from last activity - RegistrationMainActivity
                createNewUser();
                createUserDetails();
                // TODO - in here we will need to direct the user to the app page with all the matches and profiles, but only if the user finished registration.
                Intent intent = new Intent(RegistrationSongsActivity.this, MatchActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void makeSong() {
        song = new Song();
        song.setSong("Envolver");
        song.setArtist("Anitta");
        song.setImage("https://i.scdn.co/image/ab67616d0000b2739a508628257cebd4539116cc");

        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song);
        SongAdapter songAdapter = new SongAdapter(this, songs);
        songs_LST_songs.setLayoutManager(new LinearLayoutManager(this));
        songs_LST_songs.setHasFixedSize(true);
        songs_LST_songs.setAdapter(songAdapter);
        //checkBox = findViewById(R.id.checkBox);
        //chosenSongs = new ArrayList<>();
    }

    private void findViews() {
        songs_LBL_trackName = findViewById(R.id.songs_LBL_trackName);
        songs_LBL_artistName = findViewById(R.id.songs_LBL_artistName);
        songs_LST_songs = findViewById(R.id.songs_LST_songs);
        //checkBox = findViewById(R.id.checkBox);
        songs_IMG_image = findViewById(R.id.songs_IMG_image);
        songs_BTN_finish = findViewById(R.id.songs_BTN_finish);

    }

    private void makeUser() {
        user = new User();
        user.setEmail(userDetails[0]);
        user.setUsername(userDetails[1]);
        user.setAvatar("img");
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new User.userJsonSerializer()).create();
        jsonBody = gson.toJson(user);

        jsonDetails="{\n" +
                "    \"name\":\""+userDetails[0]+"\",\n" +
                "    \"type\":\"profile\",\n" +
                "    \"createdBy\":{\n" +
                "        \"userId\":{\n" +
                "            \"domain\":\"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"email\":\"avivit.yehezkel@s.afeka.ac.il\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"instanceAttributes\":{\n" +
                "        \"firstname\":\""+userDetails[1]+"\",\n" +
                "        \"lastname\":\""+userDetails[2]+"\",\n" +
                "        \"birthdate\":\""+userDetails[3]+"\",\n" +
                "        \"gender\":\""+userDetails[4]+"\",\n" +
                "        \"interestedin\":\""+userDetails[5]+"\",\n" +
                "        \"occupation\":\""+userDetails[6]+"\",\n" +
                "        \"bio\":\""+userDetails[7]+"\",\n" +
                "        \"chosenArtists\":\""+chosenArtists+"\",\n" +
                "        \"chosenArtists\":\""+song.toString()+"\"\n" +
                "    }\n" +
                "\n" +
                "}";
    }

//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkBox:
//                if (checked) {
//                    ll1.setBackgroundColor(Color.parseColor("#5C9A4141"));
//                    chosenSongs.add(checkBox.getText().toString());
//                }
//                else {
//                    ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    chosenSongs.remove(checkBox.getText().toString());
//                }
//                break;
//        }
//    }


    private void createNewUser(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/users";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        //Toast.makeText(RegistrationSongsActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(RegistrationSongsActivity.this, "user create = " + error, Toast.LENGTH_SHORT).show();
                Log.d("ccc",""+error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }

    private void createUserDetails(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/instances";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        //Toast.makeText(RegistrationSongsActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            Log.d("ccc",""+respObj.toString());

                        } catch (JSONException e) {
                            Log.d("ccc",""+e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                //TODO:tell the user if email already in the system
                Toast.makeText(RegistrationSongsActivity.this, "instance create = " + error, Toast.LENGTH_SHORT).show();
                Log.d("ccc",""+error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonDetails == null ? null : jsonDetails.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonDetails, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }



}