package com.example.duet.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView profile_TXT_likes, profile_TXT_matches, profile_TXT_photos, EditProfile, SaveProfile, logOut;
    private EditText emailInfo , profile_TXT_bio, nameInfo, birthdateInfo, genderInfo, intrestedInfo, occupationInfo;
    private EditText artistsInfo,songInfo;
    private BottomNavigationView bottomNavigationView;
    //private boolean isSpotify;
    private String email="";
    private String userId="";
    private String jsonDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViews();
        email = getIntent().getStringExtra("email");
        //isSpotify=getIntent().getBooleanExtra("spotify",false);
//        if(isSpotify){
//            getUserDetailsFromSpotify();
//        }else {
        getUserDetails(email);
        //}
        //setUserDetails();
        initNavigation();
        EditProfile.setOnClickListener(view -> editDetails());
        SaveProfile.setOnClickListener(view -> saveDetails());
    }

    private void editDetails() {
        emailInfo.setEnabled(true);
        emailInfo.setTextColor(Color.RED);
        nameInfo.setEnabled(true);
        nameInfo.setTextColor(Color.RED);
        genderInfo.setEnabled(true);
        genderInfo.setTextColor(Color.RED);
        intrestedInfo.setEnabled(true);
        intrestedInfo.setTextColor(Color.RED);
        occupationInfo.setEnabled(true);
        occupationInfo.setTextColor(Color.RED);
        birthdateInfo.setEnabled(true);
        birthdateInfo.setTextColor(Color.RED);
        profile_TXT_bio.setEnabled(true);
        profile_TXT_bio.setTextColor(Color.RED);
    }

    private void saveDetails() {
        emailInfo.setEnabled(false);
        emailInfo.setTextColor(Color.BLACK);
        nameInfo.setEnabled(false);
        nameInfo.setTextColor(Color.BLACK);
        genderInfo.setEnabled(false);
        genderInfo.setTextColor(Color.BLACK);
        intrestedInfo.setEnabled(false);
        intrestedInfo.setTextColor(Color.BLACK);
        occupationInfo.setEnabled(false);
        occupationInfo.setTextColor(Color.BLACK);
        birthdateInfo.setEnabled(false);
        birthdateInfo.setTextColor(Color.BLACK);
        profile_TXT_bio.setEnabled(false);
        profile_TXT_bio.setTextColor(Color.BLACK);
        createJson();
        setUserDetails();
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });
    }

//    private void getUserDetailsFromSpotify() {
//    }

    private void findViews() {
        profile_TXT_likes = findViewById(R.id.profile_TXT_likes);
        profile_TXT_matches = findViewById(R.id.profile_TXT_matches);
        profile_TXT_photos = findViewById(R.id.profile_TXT_photos);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.person);
        emailInfo = findViewById(R.id.emailInfo);
        nameInfo = findViewById(R.id.nameInfo);
        birthdateInfo = findViewById(R.id.birthdateInfo);
        genderInfo = findViewById(R.id.genderInfo);
        intrestedInfo = findViewById(R.id.intrestedInfo);
        occupationInfo = findViewById(R.id.occupationInfo);
        profile_TXT_bio = findViewById(R.id.profile_TXT_bio);
        EditProfile= findViewById(R.id.EditProfile);
        EditProfile.setPaintFlags(EditProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SaveProfile= findViewById(R.id.SaveProfile);
        SaveProfile.setPaintFlags(SaveProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logOut= findViewById(R.id.logOut);
        logOut.setPaintFlags(logOut.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        artistsInfo=findViewById(R.id.artistsInfo);
        songInfo=findViewById(R.id.songInfo);

    }
    private void getUserDetails(String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://192.168.0.106:8085/iob/instances/search/byName/" + userEmail +"?userDomain=2022b.Yaeli.Bar.Gimelshtei&userEmail=avivit.yehezkel@s.afeka.ac.il";
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
                            JSONObject id = details.getJSONObject("instanceId");
                            userId = id.getString("id");
                            emailInfo.setText(details.getString("name"));
                            nameInfo.setText(attr.getString("firstname") +" "+ attr.getString("lastname"));
                            birthdateInfo.setText(attr.getString("birthdate"));
                            genderInfo.setText(attr.getString("gender"));
                            intrestedInfo.setText(attr.getString("interestedin"));
                            occupationInfo.setText(attr.getString("occupation"));
                            profile_TXT_bio.setText(attr.getString("bio"));
                            artistsInfo.setText(attr.getString("chosenArtists"));
                            songInfo.setText(attr.getString("chosenSong"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ccc","catch error profile instance "+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ccc","error profile instance "+error.toString());
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

    private void createJson() {
        String[] name = nameInfo.getText().toString().split(" ");
        String first = name[0];
        String last = name[1];
        jsonDetails="{\n" +
                "    \"instanceAttributes\":{\n" +
                "        \"firstname\":\""+first+"\",\n" +
                "        \"lastname\":\""+last+"\",\n" +
                "        \"birthdate\":\""+birthdateInfo.getText()+"\",\n" +
                "        \"gender\":\""+genderInfo.getText()+"\",\n" +
                "        \"interestedin\":\""+intrestedInfo.getText()+"\",\n" +
                "        \"occupation\":\""+occupationInfo.getText()+"\",\n" +
                "        \"bio\":\""+profile_TXT_bio.getText()+"\",\n" +
                "        \"chosenArtists\":\"\",\n" +
                "        \"chosenSong\":\"\"\n" +
                "    }\n" +
                "}";
    }

    private void setUserDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://192.168.0.106:8085/iob/instances/2022b.Yaeli.Bar.Gimelshtei/" + userId +"?userDomain=2022b.Yaeli.Bar.Gimelshtei&userEmail=avivit.yehezkel@s.afeka.ac.il";
        StringRequest request = new StringRequest(Request.Method.PUT, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONArray respObj = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ccc","catch error profile update instance "+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ccc","error profile update instance "+error.toString());
            }
        }){
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

    private void initNavigation() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        //move to profile home
                        Intent home=new Intent(getApplicationContext(),MatchActivity.class);
                        home.putExtra("email",email);
                        startActivity(home);
                        finish();
                        return true;

                    case R.id.chats:
                        //move to chat activity
                        Intent chats=new Intent(getApplicationContext(),ChatsActivity.class);
                        chats.putExtra("email",email);
                        startActivity(chats);
                        finish();
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