package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.whatsapp_part_4.Adapter.UserGetAdapter;
import com.example.whatsapp_part_4.Dialog.AddFriendDialogFragment;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;
import com.example.whatsapp_part_4.databinding.ActivityFriendsBinding;

import java.util.List;

public class friends extends AppCompatActivity implements AddFriendDialogFragment.AddFriendDialogListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFriendsBinding binding;
    private Model model;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = DatabaseSingleton.getModel(this);
        if (model.getTheme() != null) {
            Log.e("TAG", "onCreate: " + model.getTheme().getTheme());
            setTheme(model.getTheme().getTheme());
        }

        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.list_item_text2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("friends", "first act");

        Intent intent = getIntent();
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


        username = intent.getStringExtra("username");



            ImageView profileImageView = toolbar.findViewById(R.id.profileImageView);//todo need to add the image and the name
            TextView displayNameTextView = toolbar.findViewById(R.id.displayNameTextView);
            Log.e("TAG", "onCreate: "+username );
            displayNameTextView.setText(username);
            Log.e("TAG", "onCreate: "+model.getUserDisplayname()+model.getUserDisplayname() );




        model.reload();
        //add sychronized
        // Observe changes to the messages data
        UserGetAdapter adapter = new UserGetAdapter(model.getUsersget().getValue(), username);
        recyclerView.setAdapter(adapter);
        model.getUsersget().observe(this, new Observer<List<UserGet>>() {
            @Override
            public void onChanged(List<UserGet> users) {
                // Update the adapter with the new messages data

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
                model.clearLogoutUser();
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