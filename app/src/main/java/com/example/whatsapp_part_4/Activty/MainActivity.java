package com.example.whatsapp_part_4.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.whatsapp_part_4.data.ConstantData;
import com.example.whatsapp_part_4.data.DataUserRes;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

/**
 * The main activity of the WhatsApp-like application.
 */
public class MainActivity extends AppCompatActivity implements OptionsDialog.OnOptionSelectedListener {

    private Appdb db;
    private Model model;
    private String password;
    private String username;
    private ActivityMainBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = DatabaseSingleton.getModel(this);

        // Set the activity theme based on the theme stored in the database
        if (model.getTheme() != null) {
            setTheme(model.getTheme().getTheme());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbarformainactivity);
        setSupportActionBar(toolbar);

        // Check and request notification permission
        NotificationPermissionHandler notificationPermissionHandler = new NotificationPermissionHandler(this);
        notificationPermissionHandler.checkAndRequestPermission();

        // Set up login button
        loginButton();

        // Set up click listener for the clickable text
        binding.clickableText.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

        // Set up text change listeners for username and password EditText fields
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
     * Register the user to Firebase for real-time notifications and messages.
     *
     * @param username The username of the user.
     * @param model    The model instance.
     */
    public static void Registerfirebase(String username, Model model) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String tokenfirebase = task.getResult();
                model.registerFireBase(username, tokenfirebase).thenApply(statusCode -> {
                    if (statusCode == 200) {
                        // Registration successful
                    }
                    return null;
                });
            }
        });
    }

    /**
     * Set up the login button click listener.
     */
    private void loginButton() {
        binding.loginButton.setOnClickListener(view -> {
            CompletableFuture<Integer> future = model.tryLogin(username, password);
            future.thenApply(statusCode -> {
                if (statusCode == 200) {
                    CompletableFuture<DataUserRes> future2 = model.getUserData(username);
                    future2.thenAccept(userData -> {
                        if (userData != null) {
                            Intent intent = new Intent(this, Friends.class);
                            intent.putExtra("username", username);
                            intent.putExtra("displayName", userData.getDisplayName());
                            intent.putExtra("profilePic", userData.getProfilePic());
                            intent.putExtra("token", model.getToken());
                            UserTimeZoneExample();
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

    /**
     * Create the options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_mainactivty, menu);
        return true;
    }

    /**
     * Handle option menu item selection.
     *
     * @param item The menu item that was selected.
     * @return true to consume the event here, or false to allow normal menu processing to proceed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu:
                // Show the options dialog with theme options
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

    /**
     * Handle the selected option from the options dialog.
     *
     * @param themeOption The selected theme option.
     */
    @Override
    public void onOptionSelected(ThemeOption themeOption) {
        setTheme(R.style.AppThemeread);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        model.setTheme(themeOption.getNameOfRes());
        restartApp();
    }

    /**
     * Restart the application after changing the theme.
     */
    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    /**
     * Example function to get the user's time zone.
     */
    public void UserTimeZoneExample() {
        TimeZone userTimeZone = TimeZone.getDefault();
        // Print the time zone ID and display name
        ConstantData.TimeZoneId = userTimeZone.getID();
    }
}
