package com.example.whatsapp_part_4.Services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.whatsapp_part_4.Activty.Chat;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Objects;

/**
 * FireBaseMsg class is responsible for handling Firebase Cloud Messaging (FCM) messages.
 */
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FireBaseMsg extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "channel_id";

    public FireBaseMsg() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            // Handle notification message
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getData().get("content");
            showNotification(title, body);
        }

        if (remoteMessage.getData().size() > 0) {
            // Handle data message
            String content = remoteMessage.getData().get("content");
            String created = remoteMessage.getData().get("created");
            String id = remoteMessage.getData().get("id");
            String sender = remoteMessage.getData().get("sender");
            Gson gson = new Gson();
            Message.Sender senderObject = gson.fromJson(sender, Message.Sender.class);
            Model model = DatabaseSingleton.getModel(this);
            model.reloadUsersInTheBack(); // Update the users' time
            if (Objects.equals(Chat.friendusername, senderObject.getUsername())) {
                model.addMessage(new Message(id, senderObject, content, created));
            }
        }
    }

    /**
     * Shows a notification with the given title and body.
     *
     * @param title The title of the notification.
     * @param body  The body of the notification.
     */
    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android 8.0 and higher
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(false);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }
}