package com.example.whatsapp_part_4.Activty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Adapter.SpaceItemDecoration;
import com.example.whatsapp_part_4.Async.AsyncTaskMessage;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.databinding.ActivityChatBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The Chat activity in the WhatsApp-like application.
 */
public class Chat extends AppCompatActivity {

    // Declare class variables
    private Model model;
    private String userIdFriend;
    static public String friendUserName;
    private ActivityChatBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @SuppressLint("NotifyDataSetChanged")
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
        model.getMessages().observe(this, messages -> {
            adapter.setMessages(messages);
            adapter.notifyDataSetChanged();
        });

        // Retrieve data from the previous activity
        Intent intent = getIntent();
        String displayName = intent.getStringExtra("displayName");
        String profilePic = intent.getStringExtra("profilePic");
        userIdFriend = intent.getStringExtra("userId");
        friendUserName = intent.getStringExtra("friendUserName");

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

        //get messages from db and from server with async task
        ProgressBar progressBar = findViewById(R.id.progressBar);

        AsyncTaskMessage asyncTaskMesseges = new AsyncTaskMessage(model, this.userIdFriend,progressBar);
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
                Log.e("TAG", "onCreate: " + userIdFriend );
                model.sendMessage(userIdFriend, message, friendUserName);
                binding.inputField.setText("");
            }
        });
    }
}
