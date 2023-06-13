package com.example.whatsapp_part_4.Activty;

import static com.example.whatsapp_part_4.Activty.MainActivity.Registerfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsapp_part_4.Adapter.UserGetAdapter;
import com.example.whatsapp_part_4.Dialog.AddFriendDialogFragment;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;
import com.example.whatsapp_part_4.databinding.ActivityFriendsBinding;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class friends extends AppCompatActivity implements AddFriendDialogFragment.AddFriendDialogListener {

    public static String username;
    private AppBarConfiguration appBarConfiguration;
    private ActivityFriendsBinding binding;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = DatabaseSingleton.getModel(this);
        Intent intent = getIntent();

        username = intent.getStringExtra("username");

        if (model.getTheme() != null) {
            Log.e("TAG", "onCreate: " + model.getTheme().getTheme());
            setTheme(model.getTheme().getTheme());
        }
        Log.e("TAG", "onCreate: " + model.getTheme());
        if (model.getlstuserlogin() != null) {
            String usernamelast = model.getlstuserlogin();
            if (usernamelast.equals(username)){

            }
            else{
                model.clearLogoutUser();
                model.setlstuserlogin(username);
            }
        }
        else {
            model.setlstuserlogin(username);
        }
        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.list_item_text2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("friends", "first act");

        Log.d("friends", "2nd act");

        if (intent != null) {
            Log.d("friends", "3rd act");

            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    Log.d("IntentExtra", "Key: " + key + ", Value: " + value);
                }
            }

        }


        String displayName = intent.getStringExtra("displayName");
        String profilePic = intent.getStringExtra("profilePic");
        Registerfirebase(username,model);
        ImageView profileImageView = toolbar.findViewById(R.id.profileImageView);//todo need to add the image and the name
        TextView displayNameTextView = toolbar.findViewById(R.id.displayNameTextView);
        Log.e("TAG", "onCreate: " + username);
        displayNameTextView.setText(displayName);
        Log.e("TAG", "onCreate: " + model.getUserDisplayname() + model.getUserDisplayname());
        //Picasso.get().load(profilePic).into(profileImageView);
        Glide.with(this)
                .load(profilePic)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImageView);

        model.reload();
        //add sychronized
        // Observe changes to the messages data
        UserGetAdapter adapter = new UserGetAdapter(model.getUsersget().getValue(), username);
        recyclerView.setAdapter(adapter);
        model.getUsersget().observe(this, new Observer<List<UserGet>>() {
            @Override
            public void onChanged(List<UserGet> users) {
                // Update the adapter with the new messages data
                users.sort( new Comparator<UserGet>() {
                    @Override
                    public int compare(UserGet user1, UserGet user2) {
                        String created1 = null;
                        String created2 = null;

                        if (user1.getLastMessage() != null) {
                            created1 = user1.getLastMessage().getCreated();
                            Log.e("TAG", "compare:1 " +created1);

                        }
                        if (user2.getLastMessage() != null) {
                            created2 = user2.getLastMessage().getCreated();
                            Log.e("TAG", "compare:2 " +created2);
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

                Log.e("TAG", "onChanged:model.getUsersget().observe " + "users" );
                adapter.setUserGetList(users);
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_friend:
                showAddFriendDialog();
                return true;
            case R.id.logout:
                model.sendTokenfirebasedel(username);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAddFriendDialog() {
        AddFriendDialogFragment dialog = new AddFriendDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddFriendDialogFragment");
    }

    @Override
    public void onAddFriendDialogPositiveClick(String friendName) {
        // Handle adding the new friend here
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.reload();

    }

}