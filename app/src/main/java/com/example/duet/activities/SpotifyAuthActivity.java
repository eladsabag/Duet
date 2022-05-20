package com.example.duet.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.MSP;
import com.example.duet.R;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpotifyAuthActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;

    private RequestQueue queue;

    String CLIENT_ID = "20a43da3a3d6461a939c9b70211eff0b";
    private static final int REQUEST_CODE = 1337;
    private static final boolean SHOW_DIALOG = true;
    private static final String REDIRECT_URI = "com.example.duet://callback";
    private static final String SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private";
    //String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_spotify_auth);
        authenticateSpotify();
        msharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);

    }


    private void authenticateSpotify() {
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES}).setShowDialog(SHOW_DIALOG);
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    editor = getSharedPreferences("SP_FILE", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    //Log.d("STARTING", "GOT AUTH TOKEN");
                    editor.apply();
                    startMainActivity();
                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.d("error","didn't get token");
                    // Handle error response
                    break;
                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }
    private void waitForUserInfo() {
//        UserService userService = new UserService(queue, msharedPreferences);
//        userService.get(() -> {
//            User user = userService.getUser();
//            editor = getSharedPreferences("SPOTIFY", 0).edit();
//            editor.putString("userid", user.id);
//            Log.d("STARTING", "GOT USER INFORMATION");
//            // We use commit instead of apply because we need the information stored immediately
//            editor.commit();
//            startMainActivity();
//        });
    }
    private void startMainActivity(){
        String ENDPOINT = "https://api.spotify.com/v1/me";
        RequestQueue mqueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
            String email=response.optString("email");
            getUser(email);

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = MSP.getMe().getString("token","");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);
    }


    private void getUser(String email) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://10.0.0.11:8085/iob/users/login/2022b.Yaeli.Bar.Gimelshtei/" + email;
        StringRequest request = new StringRequest(Request.Method.GET, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // on below line we are displaying a success toast message.
                        try {
                            //response json
                            JSONObject respObj = new JSONObject(response);
                            Intent newintent = new Intent(SpotifyAuthActivity.this, MatchActivity.class);
                            newintent.putExtra("email",email);
                            startActivity(newintent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Intent newintent = new Intent(SpotifyAuthActivity.this, RegistrationFormForSpotifyUser.class);
                            newintent.putExtra("email",email);
                            startActivity(newintent);

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



}