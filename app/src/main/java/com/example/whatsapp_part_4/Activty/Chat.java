package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.databinding.ActivityChatBinding;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

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
    private String userIdfriend;

   static public String friendusername;

    private String username;

    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= DatabaseSingleton.getModel(this);
        if (model.getTheme()!=null){
            Log.e("TAG", "onCreate: "+model.getTheme().getTheme() );
            setTheme(model.getTheme().getTheme());
        }
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//       String user= getIntent().getParcelableExtra("user");
//        String token= getIntent().getParcelableExtra("token");

//        model.getMessgesByuser("99");
        RecyclerView recyclerView = findViewById(R.id.list_item_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MessageAdapter adapter = new MessageAdapter(model.getMessages().getValue());
        recyclerView.setAdapter(adapter);
        //add sychronized
        // Observe changes to the messages data
        model.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                // Update the adapter with the new messages data
                adapter.setMessages(messages);
                adapter.notifyDataSetChanged();
            }
        });
        Intent intent = getIntent();
        displayName = intent.getStringExtra("displayName");
        profilePic = intent.getStringExtra("profilePic");
        userIdfriend = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        friendusername = intent.getStringExtra("friendusername");
        model.getMessgesByuser(userIdfriend);
        binding.sendButton.setOnClickListener(v -> {
            String message = binding.inputField.getText().toString();
            if (!message.isEmpty()) {
                byte[] decodedString = Base64.decode(profilePic, Base64.DEFAULT);
                model.sendMessage(userIdfriend, message,username, displayName, decodedString,friendusername);
                binding.inputField.setText("");
            }
        });

    }
}