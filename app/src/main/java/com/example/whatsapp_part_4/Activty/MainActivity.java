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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        model = DatabaseSingleton.getModel(this);
        if (model.getTheme() != null) {//get theme from db dynamic by id
            setTheme(model.getTheme().getTheme());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbarformainactivity);
        setSupportActionBar(toolbar);


        NotificationPermissionHandler notificationPermissionHandler = new NotificationPermissionHandler(this);
        notificationPermissionHandler.checkAndRequestPermission();//check if can make notificationma
        loginButton();//login button
        binding.clickableText.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });


        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    /**
     * Registerfirebase function-for real time notification and msg. register the user to server
     * @param username ther username of the user
     * @param model the model
     */
    public static void Registerfirebase(String username, Model model) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String tokenfirebase = task.getResult();
                model.registerfirebase(username, tokenfirebase).thenApply(statusCode -> {
                    if (statusCode == 200) {

                    }
                    return null;
                });

            }

        });

    }




    private void loginButton() {
        binding.loginButton.setOnClickListener(view -> {
            CompletableFuture<Integer> future = model.trylogin(username, password);
            future.thenApply(statusCode -> {
                if (statusCode == 200) {
                    CompletableFuture<DataUserRes> future2 = model.getUserData(username);
                    future2.thenAccept(userData -> {
                        if (userData != null) {
                            Intent intent = new Intent(this, Friends.class);
                            intent.putExtra("username", username);
                            intent.putExtra("displayName", userData.getDisplayName());
                            intent.putExtra("profilePic", userData.getProfilePic());
                            intent.putExtra("token", model.gettoken());
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Error server", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
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
        model.setTheme(themeOption.getNameofres());//set theme in db
        restartApp();//restart app
    }


    /***
     * restart app function when change theme
     */
    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

}