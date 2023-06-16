package com.example.whatsapp_part_4.Activty;

import static com.example.whatsapp_part_4.Activty.MainActivity.Registerfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Adapter.UserGetAdapter;
import com.example.whatsapp_part_4.Dialog.AddFriendDialogFragment;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;
import com.example.whatsapp_part_4.databinding.ActivityFriendsBinding;

import java.util.Comparator;
import java.util.List;

/**
 * The Friends activity in the WhatsApp-like application.
 */
public class Friends extends AppCompatActivity implements AddFriendDialogFragment.AddFriendDialogListener {

    public static String username;
    private ActivityFriendsBinding binding;
    private Model model;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the model and retrieve data from the previous activity
        model = DatabaseSingleton.getModel(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Set the activity theme based on the theme stored in the database
        if (model.getTheme() != null) {
            setTheme(model.getTheme().getTheme());
        }

        // Check if the user is the same user that logged in last time, if not clear the database
        if (model.getlstuserlogin() != null) {
            String usernamelast = model.getlstuserlogin();
            if (!usernamelast.equals(username)) {
                model.clearLogoutUser();
                model.setlstuserlogin(username);
            }
        } else {
            model.setlstuserlogin(username);
        }

        // Set the activity layout
        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the RecyclerView for displaying users
        RecyclerView recyclerView = findViewById(R.id.list_item_text2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve display name and profile picture from the intent
        String displayName = intent.getStringExtra("displayName");
        String profilePic = intent.getStringExtra("profilePic");
        Registerfirebase(username, model);

        // Set the profile picture and display name in the toolbar
        ImageView profileImageView = toolbar.findViewById(R.id.profileImageView);
        TextView displayNameTextView = toolbar.findViewById(R.id.displayNameTextView);
        displayNameTextView.setText(displayName);

        // Decode the profile picture from Base64 and set it to the ImageView
        String[] parts = profilePic.split(",");
        if (parts.length > 1) {
            String imageString = parts[1];
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImageView.setImageBitmap(decodedByte);
        }

        // Set up the UserGetAdapter and observe changes to the users data
        UserGetAdapter adapter = new UserGetAdapter(model.getUsersget().getValue(), username);
        recyclerView.setAdapter(adapter);
        model.getUsersget().observe(this, new Observer<List<UserGet>>() {
            @Override
            public void onChanged(List<UserGet> users) {
                // Sort the users based on the last message
                users.sort(new Comparator<UserGet>() {
                    @Override
                    public int compare(UserGet user1, UserGet user2) {
                        String created1 = null;
                        String created2 = null;

                        if (user1.getLastMessage() != null) {
                            created1 = user1.getLastMessage().getCreated();
                        }
                        if (user2.getLastMessage() != null) {
                            created2 = user2.getLastMessage().getCreated();
                        }

                        // Handle null values
                        if (created1 == null && created2 == null) {
                            return 0;
                        } else if (created1 == null) {
                            return 1; // Place null values at the end
                        } else if (created2 == null) {
                            return -1; // Place null values at the end
                        }

                        // Compare the created dates
                        return created2.compareTo(created1); // Use created1.compareTo(created2) for ascending order
                    }
                });

                // Update the adapter with the new users data
                adapter.setUserGetList(users);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in the options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return true to consume the event here, or false to allow normal menu processing to proceed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_friend:
                showAddFriendDialog();
                return true;
            case R.id.logout:
                model.sendTokenFireBaseDel(username); // Delete the token from Firebase
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Show the dialog for adding a new friend.
     */
    public void showAddFriendDialog() {
        AddFriendDialogFragment dialog = new AddFriendDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddFriendDialogFragment");
    }

    /**
     * Called when the positive button of the AddFriendDialogFragment is clicked.
     *
     * @param friendName The name of the friend to add.
     */
    @Override
    public void onAddFriendDialogPositiveClick(String friendName) {
        // Handle adding the new friend here
    }

    /**
     * Called when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        model.reload();
    }
}
