package com.example.whatsapp_part_4;

import android.os.Bundle;

import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Chat extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private Appdb db;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        RecyclerView recyclerView = findViewById(R.id.list_item_text);
       String user= getIntent().getParcelableExtra("user");
        String token= getIntent().getParcelableExtra("token");
        model= DatabaseSingleton.getModel(this);
        model.getMessgesByuser("99");
        //add sychronized
         List<Message> messages =model.getMessages().getValue();
        MessageAdapter adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);


    }
}