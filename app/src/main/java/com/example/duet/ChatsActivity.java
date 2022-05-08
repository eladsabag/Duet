package com.example.duet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity {
    private RecyclerView chats_LST_chats;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        chats_LST_chats = findViewById(R.id.chats_LST_chats);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.chats);

        initNavigation();

        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        ChatAdapter liqueurAdapter = new ChatAdapter(this, chats);
        chats_LST_chats.setLayoutManager(new LinearLayoutManager(this));
        chats_LST_chats.setHasFixedSize(true);
        chats_LST_chats.setAdapter(liqueurAdapter);
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
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        //overridePendingTransition(0,0);
                        return true;

                    case R.id.chats:
                        //stay in this activity
                        return true;

                    case R.id.person:
                        //move to profile activity
                        //startActivity(new Intent(getApplicationContext(),About.class));
                        //overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }
}