package com.example.whatsapp_part_4.Activty;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.databinding.ActivityRegisterBinding;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.navigation.ui.AppBarConfiguration;


import com.example.whatsapp_part_4.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Register extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Model model;
    private ImageView profileImageButton;
    private Uri selectedImageUri;
    String username;
    String password;
    String displayName;
    String img;
    boolean isImageSelected = false;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    ActivityRegisterBinding binding;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = DatabaseSingleton.getModel(this);
        if (model.getTheme() != null) {//get theme from db dynamic by id
            setTheme(model.getTheme().getTheme());
        }
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        profileImageButton = binding.profileImageView;
        setContentView(binding.getRoot());
        // make the defult image
        profileImageButton.setImageResource(R.drawable.anoymousavatar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anoymousavatar);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        img = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        binding.clickableText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick(view);
            }
        });
        // the user upload image
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        if (profileImageButton != null&& isImageSizeValid(selectedImageUri, 200 * 1024)) {
                            profileImageButton.setImageURI(selectedImageUri);
                        }else {
                            binding.msgnewuser.setText("Image size must be less than 200KB");
                            binding.msgnewuser.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }

    /**
     * file chooser from gallery
     */
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }


    /**
     * the theuser when he click on submit button
     * @param view the view
     */
    public void onSubmitButtonClick(View view) {
     {
            if (true==isImageSelected){

                img = convertImageToBase64(selectedImageUri);
                img = "data:image/jpeg;base64," + img;
            }
            else {
                img = "data:image/jpeg;base64," + img;
            }
            username = binding.usernameEditText.getText().toString();
            password = binding.passwordEditText.getText().toString();
            displayName = binding.displayNameEditText.getText().toString();
            if (password.length() < 8) {

                binding.msgnewuser.setText("Password must be at least 8 characters");
                binding.msgnewuser.setVisibility(View.VISIBLE);
                return;
            }
            if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {

                binding.msgnewuser.setText("Please fill all fields");
                binding.msgnewuser.setVisibility(View.VISIBLE);

            } else {
                // make new user on update the server, if can make new user then login by get trylogin
                AtomicInteger resulsave= new AtomicInteger();
                CompletableFuture<Integer> integerCompletableFuture = model.makenewuser(username, password, displayName, img);
                integerCompletableFuture.thenCompose((result) -> {
                    resulsave.set(result);
                    if (result == 200) {
                        return model.trylogin(username, password);
                    } else {
                        // Handle the case where creating a new user was not successful
                        return CompletableFuture.completedFuture(-1); // Return a completed future with an error code (-1)
                    }
                }).thenApply((res) -> {
                    //after trylogin go to friends
                    if (res == 200) {
                        Intent intent = new Intent(this, Friends.class);
                        intent.putExtra("username", username);
                        intent.putExtra("displayName", displayName);
                        intent.putExtra("profilePic", img);

                        startActivity(intent);
                        finish();
                    } else if (res == -1) {
                        // Handle the case where creating a new user was not successful
                        if (resulsave.get() == 409) {
                            binding.msgnewuser.setText("Username already exists");
                        } else if (resulsave.get() == 400) {
                            binding.msgnewuser.setText("Invalid request");
                        }
                        binding.msgnewuser.setVisibility(View.VISIBLE);
                    } else {
                        // Handle the case where login was not successful
                    }
                    return null;
                });
            }


     }


    }

    /**
     * convert image to base64
     * @param imageUri the image uri
     * @return the image in base64 jpeg format
     */
    private String convertImageToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * check if the image size is valid
     * @param imageUri the image uri
     * @param maxSizeInBytes the max size in bytes
     * @return true if the image size is valid
     */
    private boolean isImageSizeValid(Uri imageUri, long maxSizeInBytes) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(imageUri);

            if (inputStream != null) {
                int sizeInBytes = inputStream.available();
                inputStream.close();
                return sizeInBytes <= maxSizeInBytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}