package com.example.whatsapp_part_4.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

/**
 * Handles notification permission.
 */
public class NotificationPermissionHandler {
    private static final int PERMISSION_REQUEST_CODE = 100;

    private Activity activity;

    public NotificationPermissionHandler(Activity activity) {
        this.activity = activity;
    }

    /**
     * Checks and requests the notification permission.
     */
    public void checkAndRequestPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
                // Show an explanation dialog to the user
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Permission Required");
                builder.setMessage("This app requires permission to post notifications.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Request the permission
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // No explanation needed, request the permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            }
        }
    }
}
