package com.example.whatsapp_part_4.Activty;

import android.os.Bundle;

import com.example.whatsapp_part_4.Adapter.MessageAdapter;
import com.example.whatsapp_part_4.Adapter.UserAdapter;
import com.example.whatsapp_part_4.Adapter.UserGetAdapter;
import com.example.whatsapp_part_4.Model;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.User;
import com.example.whatsapp_part_4.data.UserGet;
import com.example.whatsapp_part_4.databinding.ActivityFriendsBinding;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ListView;

import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatsapp_part_4.R;

import java.util.List;

public class friends extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFriendsBinding binding;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView recyclerView = findViewById(R.id.list_item_text2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model= DatabaseSingleton.getModel(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model.reload();
        //add sychronized
        // Observe changes to the messages data
        model.getUsersget().observe(this, new Observer<List<UserGet>>() {
            @Override
            public void onChanged(List<UserGet> users) {
                // Update the adapter with the new messages data
                UserGetAdapter adapter = new UserGetAdapter(users);
                recyclerView.setAdapter(adapter);
            }
        });



    }}