package com.example.whatsapp_part_4.Activty;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;

import androidx.appcompat.app.AppCompatActivity;


import com.example.whatsapp_part_4.databinding.ActivityMainBinding;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private Appdb db;
    private Model model;
    private String password;
    private String username;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = DatabaseSingleton.getModel(this);
        loginButton();
//        db = Room.databaseBuilder(getApplicationContext(), Appdb.class, "maindb2")
//                .allowMainThreadQueries().build();
//        model = new Model(db);

//        model.trylogin("asafaa", "asafaa");
//
//        synchronized (model) {
//            model.trylogin("asafaa", "asafaa");
//            model.sendmsg("1", "asafaa222", "asafaa1", "asafaa", null);
//        }
//        CompletableFuture<Integer> future = model.trylogin("asafaa", "asafaa");
//        future.thenApply(statusCode -> {
//            if (statusCode == 200) {
//                model.getMessgesByuser("99");
////                model.sendmsg("99", "asafaa222", "asafaa1", "asafaa", null);
//            }
//            return null;
//        });
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        model.sendmsg("1","asafaa222","asafaa1","asafaa",null);
//        model.makenewuser("asafaa4","asafaa4","asafaa",null);
//       model.reload();
//        binding.passwordEditText.setText("asafaa");
//        binding.usernameEditText.setText("asafaa");
        password="asafaa";
        username="asafaa";
        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    password="asafaa";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something when the text changes
                password = s.toString();
                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                username="asafaa";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do something when the text changes
                username = s.toString();
                // ...
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }


    private void loginButton() {
//        String username = binding.usernameEditText.getText().toString();
//        String password = binding.passwordEditText.getText().toString();
        binding.loginButton.setOnClickListener(view -> {
            CompletableFuture<Integer> future = model.trylogin(username, password);
            future.thenApply(statusCode -> {
                if (statusCode == 200) {
                    Intent intent = new Intent(this, friends.class);
                    intent.putExtra("username", username);
//                    intent.putExtra("token", model.gettoken());
                    startActivity(intent);
//                    model.getMessgesByuser("99");
                    // model.sendmsg("99", "asafaa222", "asafaa1", "asafaa", null);
                }
                return null;
            });
        });
    }

}