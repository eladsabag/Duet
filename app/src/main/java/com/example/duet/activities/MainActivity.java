package com.example.duet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;

import com.example.duet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private MaterialTextView main_LBL_create;
    private MaterialTextView main_LBL_name;
    private MaterialTextView main_LBL_listen,main_LBL_orLogin;
    private EditText main_EDT_username,main_EDT_password;
    private MaterialButton main_BTN_login,main_BTN_spotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_BTN_login=findViewById(R.id.main_BTN_login);
        main_LBL_create=findViewById(R.id.main_LBL_create);
        main_LBL_name=findViewById(R.id.main_LBL_name);
        main_LBL_listen=findViewById(R.id.main_LBL_listen);
        main_EDT_username=findViewById(R.id.main_EDT_username);
        main_EDT_password=findViewById(R.id.main_EDT_password);
        main_LBL_orLogin=findViewById(R.id.main_LBL_orLogin);
        main_BTN_spotify=findViewById(R.id.main_BTN_spotify);

        //TASTY.otf

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Cabinet.ttf");
        Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/Momcake-Bold.otf");
        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/Rounded.ttf");
        main_LBL_listen.setTypeface(type);
        //main_LBL_name.setTypeface(type2);
        main_EDT_username.setTypeface(type3);
        main_EDT_password.setTypeface(type3);
        main_LBL_create.setTypeface(type3);
        main_LBL_orLogin.setTypeface(type3);
        //main_BTN_login.setTypeface(type3);
        //main_BTN_spotify.setTypeface(type3);


        SpannableString ss = new SpannableString(main_LBL_create.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(MainActivity.this, RegistrationMainActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 23, main_LBL_create.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        main_LBL_create.setText(ss);
        main_LBL_create.setMovementMethod(LinkMovementMethod.getInstance());
        main_LBL_create.setHighlightColor(Color.TRANSPARENT);

        SpannableString ss1=new SpannableString(main_LBL_create.getText());
        ForegroundColorSpan fgcs=new ForegroundColorSpan(Color.RED);
        ss1.setSpan(fgcs,23,main_LBL_create.getText().length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        main_LBL_create.setText(ss1);


        main_BTN_spotify.setOnClickListener(view -> spotifyLogin());

    }

    private void spotifyLogin() {
        startActivity(new Intent(MainActivity.this, SpotifyAuthActivity.class));
    }
}