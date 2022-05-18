package com.example.duet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.duet.R;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class SpotifyAuthActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;

    private RequestQueue queue;

    String CLIENT_ID = "20a43da3a3d6461a939c9b70211eff0b";
    private static final int REQUEST_CODE = 1337;
    private static final boolean SHOW_DIALOG = true;
    private static final String REDIRECT_URI = "com.example.test://callback";
    private static final String SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private";

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
                    Log.d("STARTING", "GOT AUTH TOKEN");
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

    private void startMainActivity() {
        Intent newintent = new Intent(SpotifyAuthActivity.this, MatchActivity.class);
        newintent.putExtra("spotify",getIntent().getBooleanExtra("spotify",true));
        startActivity(newintent);
    }

}