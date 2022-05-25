package com.example.duet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.duet.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private MaterialTextView match_LBL_profile,match_LBL_job;
    private TextView match_LBL_details;
    private ImageView match_IMG_img, match_IMG_like, match_IMG_dislike;
    String email;
    User user = new User();
    String matchBody ="";
    String likeBody ="";
    int next = 0;
    ArrayList<User> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        findViews();
        matches = new ArrayList<>();
        email = getIntent().getStringExtra("email");
        getUser(email);
        initNavigation();
        //get match
        createMatchJson();
        getMatches();
        match_IMG_like.setOnClickListener(view -> onLike());
        match_IMG_dislike.setOnClickListener(view -> setUserDetails());
        match_LBL_profile.setOnClickListener(view -> showProfile());
    }

    private void showProfile() {
        Intent profile = new Intent(getApplicationContext(),ProfileActivity.class);
        profile.putExtra("email",matches.get(next-1).getEmail());
        profile.putExtra("avatar",matches.get(next-1).getAvatar());
        profile.putExtra("isMatchProfile",true);
        startActivity(profile);
    }

    private void createMatchJson() {
        matchBody = "{\n" +
                "    \"type\": \"MATCH\",\n" +
                "    \"instance\": {\n" +
                "        \"instanceId\": {\n" +
                "            \"domain\": \"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"id\": \"af96c5fc-bcce-46e1-878a-3f52159f4a69\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"invokedBy\": {\n" +
                "        \"userId\": {\n" +
                "            \"domain\": \"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"email\": \""+email+"\"\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
    }

    private void createLikeJson() {
        likeBody = "{\n" +
                "    \"type\": \"LIKE\",\n" +
                "    \"instance\": {\n" +
                "        \"instanceId\": {\n" +
                "            \"domain\": \"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"id\": \"af96c5fc-bcce-46e1-878a-3f52159f4a69\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"invokedBy\": {\n" +
                "        \"userId\": {\n" +
                "            \"domain\": \"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"email\": \""+email+"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"activityAttributes\": {\n" +
                "        \"likeTo\": {\n" +
                "            \"userId\": {\n" +
                "            \"domain\": \"2022b.Yaeli.Bar.Gimelshtei\",\n" +
                "            \"email\": \""+matches.get(next-1).getEmail()+"\"\n" +
                "        }\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
    }

    private void findViews() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconSize(70);
        bottomNavigationView.setSelectedItemId(R.id.home);
        match_LBL_profile= findViewById(R.id.match_LBL_profile);
        match_LBL_profile.setPaintFlags(match_LBL_profile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        match_LBL_details = findViewById(R.id.match_LBL_details);
        match_LBL_job = findViewById(R.id.match_LBL_job);
        match_IMG_img = findViewById(R.id.match_IMG_img);
        match_IMG_like = findViewById(R.id.match_IMG_like);
        match_IMG_dislike = findViewById(R.id.match_IMG_dislike);
    }

    private void setUserDetails(){
        if(next < matches.size()) {
            getUserDetails(matches.get(next).getEmail());
            next++;
        }else{
            Log.d("ccc","There is a No more matches");
            Toast.makeText(this, "No More Matches :(", Toast.LENGTH_LONG).show();
        }
    }

    private void onLike() {
        createLikeJson();
        isMatch();
        setUserDetails();
    }



    private void getMatches() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/activities";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONArray respObj = new JSONArray(response);
                            for (int i =0;i<5;i++){
                                User match = new User();
                                JSONObject userJson = respObj.getJSONObject(i);
                                JSONObject userId = userJson.getJSONObject("userId");
                                match.setEmail(userId.getString("email"));
                                match.setUsername(userJson.getString("username"));
                                match.setRole(userJson.getString("role"));
                                match.setAvatar(userJson.getString("avatar"));
                                matches.add(match);
                            }
                            Log.d("ccc","matches result: "+matches.get(0).getEmail()
                            +", "+matches.get(1).getEmail()
                            +", "+matches.get(2).getEmail()
                            +", "+matches.get(3).getEmail()
                            +", "+matches.get(4).getEmail());
                            setUserDetails();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("ccc","Failed to Get Matches: "+error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return matchBody == null ? null : matchBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", matchBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }



    private void isMatch() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/activities";
        StringRequest request = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            Log.d("ccc","Checking if there is a match.. ");
                            if(Boolean.parseBoolean(respObj.getString("match"))){
                                Log.d("ccc","There is a new match between "+user.getUsername() +" And "+matches.get(next-2).getUsername());
                                newMatch();
                            };
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("ccc","Failed to check if there is a match: "+error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return likeBody == null ? null : likeBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", likeBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(request);
    }

    private void newMatch() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.match_layout, findViewById(R.id.relativeLayout));
        Toast toast = new Toast(getApplicationContext());
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    private void getUser(String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/users/login/2022b.Yaeli.Bar.Gimelshtei/" + userEmail;
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            JSONObject userId = respObj.getJSONObject("userId");
                            user.setEmail(userId.getString("email"));
                            user.setUsername(respObj.getString("username"));
                            user.setRole(respObj.getString("role"));
                            user.setAvatar(respObj.getString("avatar"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void getUserDetails(String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/instances/search/byName/" + userEmail +"?userDomain=2022b.Yaeli.Bar.Gimelshtei&userEmail=avivit.yehezkel@s.afeka.ac.il";
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //response json
                            JSONArray respObj = new JSONArray(response);
                            JSONObject details = respObj.getJSONObject(0);
                            JSONObject attr = details.getJSONObject("instanceAttributes");
                            String date = attr.getString("birthdate");
                            int year = Integer.parseInt(date.substring(date.length()-4));
                            int age = 2022-year;
                            match_LBL_details.setText(attr.getString("firstname")+" ,"+age);
                            match_LBL_job.setText(attr.getString("occupation"));
                            setImage(matches.get(next-1).getAvatar());
                            match_LBL_profile.setText("To see "+attr.getString("firstname")+"'s Profile->");
                            Log.d("ccc","Getting match details - " + attr.getString("firstname"));
                        } catch (JSONException e) {
                            Log.d("ccc","Entered catch - match details are invalid: " + e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ccc","Failed to get match details: "+error.toString());
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

    private void setImage(String encodedString) {
        Bitmap bitmap = null;
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
        }
        match_IMG_img.setImageBitmap(bitmap);
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
                        Intent chats=new Intent(getApplicationContext(),ChatsActivity.class);
                        chats.putExtra("email",email);
                        chats.putExtra("avatar",user.getAvatar());
                        startActivity(chats);
                        return true;

                    case R.id.person:
                        //move to profile activity
                        Intent profile = new Intent(getApplicationContext(),ProfileActivity.class);
                        profile.putExtra("email",email);
                        profile.putExtra("avatar",user.getAvatar());
                        startActivity(profile);
                        return true;

                }
                return false;
            }
        });

    }
}