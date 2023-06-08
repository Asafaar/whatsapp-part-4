package com.example.whatsapp_part_4.Services;

import com.example.whatsapp_part_4.Activty.Chat;
import com.example.whatsapp_part_4.Activty.MainActivity;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.Message;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class FireBaseMsg extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "channel_id";

    public FireBaseMsg() {
    }
    //TODO get the object but the nofitcaon dont work
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        remoteMessage.getMessageId();
        Log.e("TAG", "onMessageReceived: " + remoteMessage.getMessageId());
        if (remoteMessage.getNotification() != null) {
            // Handle notification message
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getData().get("content");
            Log.e("TAG", "onMessageReceived:showNotification(title, body); ");
            showNotification(title, body);
            crete();
        }

        if (remoteMessage.getData().size() > 0) {
            // Handle data message
            String content = remoteMessage.getData().get("content");
            String created = remoteMessage.getData().get("created");
            String id = remoteMessage.getData().get("id");
            String sender = remoteMessage.getData().get("sender");
            Log.e("TAG", "onMessageReceived: " + content + " " + created + " " + id + " " + sender);
            Gson gson = new Gson();
            Message.Sender senderObject = gson.fromJson(sender, Message.Sender.class);


            if (Chat.friendusername == senderObject.getUsername()) {
                Model model = DatabaseSingleton.getModel(this);
                model.addmessage(new Message(id, senderObject, content, created));

            }
        }
    }


    private void showNotification(String title, String body) {
        Log.e("TAG", "showNotification: " + title + " " + body);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android 8.0 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(false);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }


    private void crete() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int impo = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", impo);
            channel.setDescription("MyNotification");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotification")
                .setContentTitle("Friend Request")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText("You have a new friend request");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e("TAG", "crete: cant " );
            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }
}