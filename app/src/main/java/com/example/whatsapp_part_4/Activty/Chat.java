package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Adapter.SpaceItemDecoration;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.databinding.ActivityChatBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The Chat activity in the WhatsApp-like application.
 */
public class Chat extends AppCompatActivity {

    // Declare class variables
    private Model model;
    private String displayName;
    private String profilePic;
    private String userIdfriend;
    static public String friendusername;
    private String username;
    private ActivityChatBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the model and retrieve the theme from the database
        model = DatabaseSingleton.getModel(this);
        if (model.getTheme() != null) {
            setTheme(model.getTheme().getTheme());
        }

        // Set the activity layout
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView for displaying messages
        RecyclerView recyclerView = findViewById(R.id.list_item_text);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(spacingInPixels);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        MessageAdapter adapter = new MessageAdapter(model.getMessages().getValue());
        layoutManager.setReverseLayout(true);
        recyclerView.setAdapter(adapter);

        // Update the cached copy of the messages in the adapter
        model.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessages(messages);
                adapter.notifyDataSetChanged();
            }
        });

        // Retrieve data from the previous activity
        Intent intent = getIntent();
        displayName = intent.getStringExtra("displayName");
        profilePic = intent.getStringExtra("profilePic");
        userIdfriend = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        friendusername = intent.getStringExtra("friendusername");

        // Set the display name and profile picture
        ImageView profileImageView = findViewById(R.id.profileImageView);
        TextView textView = findViewById(R.id.displayNameTextView);
        textView.setText(displayName);

        // Decode the profile picture from Base64 and set it to the ImageView
        String[] parts = profilePic.split(",");
        if (parts.length > 1) {
            String imageString = parts[1];
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImageView.setImageBitmap(decodedByte);
        }

        // Set up the click listener for the send button
        binding.sendButton.setOnClickListener(v -> {
            String message = binding.inputField.getText().toString();
            if (!message.isEmpty()) {
                byte[] decodedString = null;
                try {
                    decodedString = Base64.decode(profilePic, Base64.DEFAULT);
                } catch (Exception e) {
                    // Handle exception
                }

                // Send message to the friend on the server
                model.sendMessage(userIdfriend, message, friendusername);
                binding.inputField.setText("");
            }
        });
    }
}
