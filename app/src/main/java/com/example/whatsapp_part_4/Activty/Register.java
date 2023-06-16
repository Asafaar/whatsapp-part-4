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
import android.view.View;
import android.widget.ImageView;

import com.example.whatsapp_part_4.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This activity handles the registration process for new users.
 */
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
        if (model.getTheme() != null) {
            setTheme(model.getTheme().getTheme());
        }
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        profileImageButton = binding.profileImageView;
        setContentView(binding.getRoot());
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

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        if (profileImageButton != null && isImageSizeValid(selectedImageUri, 200 * 1024)) {
                            profileImageButton.setImageURI(selectedImageUri);
                        } else {
                            binding.msgnewuser.setText("Image size must be less than 200KB");
                            binding.msgnewuser.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * Opens the file chooser for selecting an image from the gallery.
     */
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    /**
     * Handles the submit button click event.
     *
     * @param view The view that was clicked.
     */
    public void onSubmitButtonClick(View view) {
        if (isImageSelected) {
            img = convertImageToBase64(selectedImageUri);
            img = "data:image/jpeg;base64," + img;
        } else {
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
            AtomicInteger resulsave = new AtomicInteger();
            CompletableFuture<Integer> integerCompletableFuture = model.makenewuser(username, password, displayName, img);
            integerCompletableFuture.thenCompose((result) -> {
                resulsave.set(result);
                if (result == 200) {
                    return model.tryLogin(username, password);
                } else {
                    return CompletableFuture.completedFuture(-1);
                }
            }).thenApply((res) -> {
                if (res == 200) {
                    Intent intent = new Intent(this, Friends.class);
                    intent.putExtra("username", username);
                    intent.putExtra("displayName", displayName);
                    intent.putExtra("profilePic", img);
                    startActivity(intent);
                    finish();
                } else if (res == -1) {
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

    /**
     * Converts the selected image to base64 format.
     *
     * @param imageUri The URI of the selected image.
     * @return The image in base64 JPEG format.
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
     * Checks if the size of the selected image is valid.
     *
     * @param imageUri        The URI of the selected image.
     * @param maxSizeInBytes  The maximum size allowed for the image in bytes.
     * @return True if the image size is valid, false otherwise.
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
