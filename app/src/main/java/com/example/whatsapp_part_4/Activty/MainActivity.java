package com.example.whatsapp_part_4.Activty;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.whatsapp_part_4.Dialog.NotificationPermissionHandler;
import com.example.whatsapp_part_4.Dialog.OptionsDialog;
import com.example.whatsapp_part_4.Dialog.ThemeOption;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DataUserRes;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements OptionsDialog.OnOptionSelectedListener {

    private Appdb db;
    private Model model;
    private String password;
    private String username;
    private ActivityMainBinding binding;

    private String tokenfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppThemeread);
        model = DatabaseSingleton.getModel(this);
        Log.e("TAG", "onCreate: " + model.getTheme());
        if (model.getTheme() != null) {
            Log.e("TAG", "onCreate: " + model.getTheme().getTheme());
            setTheme(model.getTheme().getTheme());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbarformainactivity);
        setSupportActionBar(toolbar);


        NotificationPermissionHandler notificationPermissionHandler = new NotificationPermissionHandler(this);
        notificationPermissionHandler.checkAndRequestPermission();//check if can make notificationma
        loginButton();
        binding.clickableText.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

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
        password = "asafaa";
        username = "asafaa";
        Registerfirebase();

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                password = "asafaa";
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
                username = "asafaa";
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

    public void Registerfirebase() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                tokenfirebase = task.getResult();
                System.out.println("token: " + task.getResult());
                //todo print the errorse
                model.registerfirebase(username, tokenfirebase).thenApply(statusCode -> {
                    if (statusCode == 200) {
                        System.out.println("token: " + task.getResult());
                        Message.Sender sender = new Message.Sender();
                        sender.setUsername("asafaa");
                        sender.setDisplayName("asafaa");
                        sender.setProfilePic("asafaa");
                        Message message = new Message("116", sender, "asafaa", "asafaa");
                        Log.e("TAG", "onComplete: " + username);
                        model.sendMessageWithFirebase(message, "asafaa");
                    }
                    return null;
                });

            }

        });

    }

//    private void loginButton() {
////        String username = binding.usernameEditText.getText().toString();
////        String password = binding.passwordEditText.getText().toString();
//        binding.loginButton.setOnClickListener(view -> {
//            CompletableFuture<Integer> future = model.trylogin(username, password);
//            future.thenApply(statusCode -> {
//                if (statusCode == 200) {
//                    Intent intent = new Intent(this, friends.class);
//                    intent.putExtra("username", username);
//                    Registerfirebase();
//                    intent.putExtra("token", model.gettoken());
//                    startActivity(intent);
//                }
//                return null;
//            });
//        });
//    }


    private void loginButton() {
        // String username = binding.usernameEditText.getText().toString();
        // String password = binding.passwordEditText.getText().toString();
        binding.loginButton.setOnClickListener(view -> {
            CompletableFuture<Integer> future = model.trylogin(username, password);
            future.thenApply(statusCode -> {
                if (statusCode == 200) {
                    CompletableFuture<DataUserRes> future2 = model.getUserData(username);
                    future2.thenAccept(userData -> {
                        if (userData != null) {
                            Log.d("model", "Received object: " + userData);
                            Log.i("future2", "Is not null!");
                            Intent intent = new Intent(this, friends.class);
//                            model.setdisplayname(userData.getDisplayName());
//                            model.setprofilepic(userData.getProfilePic());
                            intent.putExtra("username", username);
                            intent.putExtra("displayName", userData.getDisplayName());
                            intent.putExtra("profilePic", userData.getProfilePic());
                            Log.i("future2", "Registerfirebase");
                            Registerfirebase();
                            Log.i("future2", "after Registerfirebase");
                            intent.putExtra("token", model.gettoken());
                            Log.i("future2", "starting activity!");
                            startActivity(intent);
                        } else {
                            Log.d("model", "Received object is null");
                        }
                    });
                }
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

                return null;
            });
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_mainactivty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu:

                ThemeOption themeOptionsDialog = new ThemeOption("Purple Theme", ContextCompat.getColor(this, R.color.seed), R.style.AppThemeread);
                ThemeOption themeOptionsDialog2 = new ThemeOption("Green Theme", ContextCompat.getColor(this, R.color.sseed), R.style.AppThemeGree);
                ThemeOption themeOptionsDialog3 = new ThemeOption("Red Theme", ContextCompat.getColor(this, R.color.ssseed), R.style.AppThemeRed);
                ThemeOption themeOptionsDialog4 = new ThemeOption("Blue Theme", ContextCompat.getColor(this, R.color.sssseed), R.style.AppThemeBlue);
                ThemeOption themeOptionsDialog5 = new ThemeOption("Dark Theme", ContextCompat.getColor(this, R.color.ssssseed), R.style.AppThemeDark);
                List<ThemeOption> themeOptions = new ArrayList<>();
                themeOptions.add(themeOptionsDialog);
                themeOptions.add(themeOptionsDialog2);
                themeOptions.add(themeOptionsDialog3);
                themeOptions.add(themeOptionsDialog4);
                themeOptions.add(themeOptionsDialog5);
                OptionsDialog optionsDialog = new OptionsDialog(this, themeOptions);
                optionsDialog.setOnOptionSelectedListener(MainActivity.this);
//                OptionsDialog optionsDialog = new OptionsDialog(this,options);
                optionsDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionSelected(ThemeOption themeOption) {
        setTheme(R.style.AppThemeread);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        model.setTheme(themeOption.getNameofres());
//        Log.e("TAG", "onOptionSelected:+asdfasdf " + themeOption.getNameofres());
//        recreate();
        restartApp();
    }

    private void setAppTheme(int themeId) {
        setTheme(themeId);
        // Recreate all activities to apply the new theme
        restartApp();
    }

    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
//    public void changeTheme(boolean isDarkMode) {
//        if (isDarkMode) {
//            setTheme(R.style.);
//        } else {
//            setTheme(R.style.AppTheme_Light);
//        }
//        setContentView(R.layout.activity_main);
//        // Update any UI elements as necessary
//    }
}