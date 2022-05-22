package com.example.duet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.R;
import com.example.duet.data.Song;
import com.example.duet.data.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RegistrationSongsActivity extends AppCompatActivity {

    private TextView text;
    private TextInputEditText songs_EDT_song, songs_EDT_artistname;
    private MaterialTextView songs_LBL_error;

    //private CheckBox checkBox;
    private MaterialButton songs_BTN_finish;
    private RecyclerView songs_LST_songs;
    private User user;
    private Song song;
    private String jsonBody;
    private String jsonDetails;
    private String[] userDetails;
    private String[] chosenArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_songs);
        userDetails = getIntent().getStringArrayExtra("userDetails");
        chosenArtists = getIntent().getStringArrayExtra("chosenArtists");
        findViews();
        makeUser();
        songs_BTN_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songs_EDT_artistname.getText().toString().length() != 0 && songs_EDT_song.getText().toString().length() != 0) {
                    makeSong();
                    makeUserDetails();
                    createNewUser();
                    createUserDetails();
                    Intent intent = new Intent(RegistrationSongsActivity.this, MatchActivity.class);
                    intent.putExtra("email",user.getEmail());
                    startActivity(intent);
                    finish();
                } else {
                    songs_LBL_error.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void makeUserDetails() {
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
                "        \"chosenArtists\":\""+ Arrays.toString(chosenArtists) +"\",\n" +
                "        \"chosenSong\":\""+song.toString()+"\"\n" +
                "    }\n" +
                "\n" +
                "}";
    }

    private void makeSong() {
        song = new Song();
        song.setSong(songs_EDT_song.getText().toString())
                .setArtist(songs_EDT_artistname.getText().toString())
                .setImage("");
    }

    private void findViews() {
        songs_EDT_song = findViewById(R.id.songs_EDT_song);
        songs_EDT_artistname = findViewById(R.id.songs_EDT_artistname);
        songs_BTN_finish = findViewById(R.id.songs_BTN_finish);
        songs_LBL_error = findViewById(R.id.songs_LBL_error);
    }

    private void makeUser() {
        user = new User();
        user.setEmail(userDetails[0]);
        user.setUsername(userDetails[1]);
        user.generateAvatar();

    }

    private void createNewUser(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://192.168.0.105:8085/iob/users";
        Log.d("ccc","jsonbody "+jsonBody);
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
                Log.d("ccc","user create "+error);
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
        String endpoint = "http://192.168.0.105:8085/iob/instances";
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
                            Log.d("ccc","info "+e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationSongsActivity.this, "instance create = " + error, Toast.LENGTH_SHORT).show();
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