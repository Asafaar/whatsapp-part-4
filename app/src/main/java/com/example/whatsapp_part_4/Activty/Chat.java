package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Adapter.SpaceItemDecoration;
import com.example.whatsapp_part_4.Async.AsyncTaskMessege;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.databinding.ActivityChatBinding;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Chat extends AppCompatActivity {


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
        model = DatabaseSingleton.getModel(this);//get model
        if (model.getTheme() != null) { //get theme from db dynamic by id
            setTheme(model.getTheme().getTheme());
        }
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.list_item_text);//the list of messages
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); //space between messages
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(spacingInPixels);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);//
        MessageAdapter adapter = new MessageAdapter(model.getMessages().getValue());//the livedata of messages
        layoutManager.setReverseLayout(true);//the messages will be from the bottom to the top
        recyclerView.setAdapter(adapter);
        // Update the cached copy of the messages in the adapter.
        model.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                // Update the adapter with the new messages data
                adapter.setMessages(messages);
                adapter.notifyDataSetChanged();
            }
        });
        //when get from friends activity
        Intent intent = getIntent();
        displayName = intent.getStringExtra("displayName");
        profilePic = intent.getStringExtra("profilePic");
        userIdfriend = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        friendusername = intent.getStringExtra("friendusername");
        ImageView profileImageView = findViewById(R.id.profileImageView);
        TextView textView = findViewById(R.id.displayNameTextView);
        textView.setText(displayName);
        String[] parts = profilePic.split(",");
        if (parts.length > 1) {
            String imageString = parts[1];
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImageView.setImageBitmap(decodedByte);
        }
        //get messages from db and from server with async task
        AsyncTaskMessege asyncTaskMesseges = new AsyncTaskMessege(model, userIdfriend);
        asyncTaskMesseges.execute();
        binding.sendButton.setOnClickListener(v -> {
            String message = binding.inputField.getText().toString();
            if (!message.isEmpty()) {
                byte[] decodedString = null;
                try {
                    decodedString = Base64.decode(profilePic, Base64.DEFAULT);
                } catch (Exception e) {
                }
                //send message to friend to server
                model.sendMessage(userIdfriend, message, username, displayName, decodedString, friendusername);
                binding.inputField.setText("");
            }
        });

    }
}