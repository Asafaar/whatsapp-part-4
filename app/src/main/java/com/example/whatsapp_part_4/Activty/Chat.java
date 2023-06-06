package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Chat extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private Appdb db;
    private Model model;
    private String displayName;
    private String profilePic;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        RecyclerView recyclerView = findViewById(R.id.list_item_text);
//       String user= getIntent().getParcelableExtra("user");
//        String token= getIntent().getParcelableExtra("token");
        model= DatabaseSingleton.getModel(this);
//        model.getMessgesByuser("99");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add sychronized
        // Observe changes to the messages data
        model.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                // Update the adapter with the new messages data
                MessageAdapter adapter = new MessageAdapter(messages);
                recyclerView.setAdapter(adapter);
            }
        });
        Intent intent = getIntent();
        displayName = intent.getStringExtra("displayName");
        profilePic = intent.getStringExtra("profilePic");
        userId = intent.getStringExtra("userId");
        model.getMessgesByuser(userId);
    }
}