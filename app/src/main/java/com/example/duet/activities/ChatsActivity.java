package com.example.duet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.duet.data.Chat;
import com.example.duet.ChatAdapter;
import com.example.duet.R;
import com.example.duet.data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {
    private RecyclerView chats_LST_chats;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        findviews();
        //ArrayList<User> users = new Gson().fromJson(getIntent().getStringExtra("users"), new TypeToken<User>(){}.getType());
        //Log.d("bbb","chats "+users.get(0).getEmail());
        initNavigation();
        setChats();
    }

    private void initNavigation() {
        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        //move to home activity
                        startActivity(new Intent(getApplicationContext(),MatchActivity.class));
                        //overridePendingTransition(0,0);
                        return true;

                    case R.id.chats:
                        //stay in this activity
                        return true;
                    case R.id.person:
                        //move to profile activity
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        //overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void setChats() {
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        ChatAdapter chatAdapter = new ChatAdapter(this, chats);
        chats_LST_chats.setLayoutManager(new LinearLayoutManager(this));
        chats_LST_chats.setHasFixedSize(true);
        chats_LST_chats.setAdapter(chatAdapter);
    }

    private void findviews() {
        chats_LST_chats = findViewById(R.id.chats_LST_chats);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.chats);
        bottomNavigationView.setItemIconSize(70);
    }
}