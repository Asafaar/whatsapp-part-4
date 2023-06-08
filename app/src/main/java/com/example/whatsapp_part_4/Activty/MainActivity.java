package com.example.whatsapp_part_4.Activty;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DatabaseSingleton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
public class MainActivity extends AppCompatActivity {

    private Appdb db;
    private Model model;
    private String password;
    private String username;
    private ActivityMainBinding binding;

    private String tokenfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = DatabaseSingleton.getModel(this);
        loginButton();
//        FirebaseMessaging.getInstance().sendToTopic("all", message);

//       FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//           @Override
//           public void onComplete(@NonNull Task<String> task) {
//               tokenfirebase=task.getResult();
//                System.out.println("token: "+task.getResult());
//           }
//       });


//        FireBaseMsg fireBaseMsg = new FireBaseMsg();
//        fireBaseMsg.sendMessageToServer("asafaa");
//        RemoteMessage.Builder builder = new RemoteMessage.Builder("your-topic")
//                .setMessageId(Integer.toString(5))
//                .addData("score", "850")
//                .addData("time", "2:45");
//// Send a message to the devices subscribed to the provided topic.
//        try {
//            FirebaseMessaging.getInstance().send(builder.build());
//            System.out.println("Message sent successfully.");
//        } catch (Exception e) {
//            System.out.println("Error sending message: " + e.getMessage());
//        }
// Response is a message ID string.
//        System.out.println("Successfully sent message: " + response);
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
//        Registerfirebase();

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

 public void Registerfirebase(){
     FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
         @Override
         public void onComplete(@NonNull Task<String> task) {
             tokenfirebase=task.getResult();
             System.out.println("token: "+task.getResult());

//              model.registerfirebase(username,tokenfirebase);
//             registerfirebase.thenApply(statusCode -> {
//                 if (statusCode == 200) {
////                     System.out.println("token: "+task.getResult());
////                     Message.Sender sender = new Message.Sender();
////                     sender.setUsername("asafaa");
////                     sender.setDisplayName("asafaa");
////                     sender.setProfilePic("asafaa");
////                     Message message = new Message("5", sender, "1111111111111111", "asafaa2");
////                     Log.e("TAG", "onComplete: "+username );
////                     model.sendMessageWithFirebase(message,"asafaa2");
//                 }
//                 return null;
//             });

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
                    Registerfirebase();
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