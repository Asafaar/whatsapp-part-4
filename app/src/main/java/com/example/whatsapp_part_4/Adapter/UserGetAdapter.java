package com.example.whatsapp_part_4.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Activty.Chat;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//todo make the list of friends more beautiful

public class UserGetAdapter extends RecyclerView.Adapter<UserGetAdapter.UserGetViewHolder> {
    private List<UserGet> userGetList;
    private String username;

    public UserGetAdapter(List<UserGet> userGetList, String username) {
        this.userGetList = userGetList;
        this.username = username;
    }

    public void setUserGetList(List<UserGet> userGetList) {
        this.userGetList = userGetList;
    }

    @NonNull
    @Override
    public UserGetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new UserGetViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGetViewHolder holder, int position) {
        UserGet userGet = userGetList.get(position);

        holder.displayNameTextView.setText(userGet.getUser().getDisplayName());
        String base64String = userGet.getUser().getProfilePic();
        String[] parts = base64String.split(",");
        if (parts.length > 1) {
            String imageString = parts[1];
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.profilePicImageView.setImageBitmap(decodedByte);
        }
        if (userGet.getLastMessage() == null) {
            holder.lastMessageTextView.setText("");
            return;
        } else {
            holder.lastMessageTextView.setText(userGet.getLastMessage().getContent());
            holder.lastMessagetimeTextView.setText(fixtime(userGet.getLastMessage().getCreated()));
        }

    }

    @Override
    public int getItemCount() {
        return userGetList.size();
    }


    public class UserGetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView profilePicImageView;
        public TextView displayNameTextView;
        public TextView lastMessageTextView;
        public TextView lastMessagetimeTextView;
        public ImageView deleteButton;
        private int position;

        Model model;

        public UserGetViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            this.position = position;
            model = DatabaseSingleton.getModel(itemView.getContext());
            profilePicImageView = itemView.findViewById(R.id.imagefrined);
            displayNameTextView = itemView.findViewById(R.id.displayname);
            lastMessageTextView = itemView.findViewById(R.id.lastmsg);
            lastMessagetimeTextView = itemView.findViewById(R.id.timestamp);
            deleteButton = itemView.findViewById(R.id.deletefriend);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.open_trash_can);
                    view.startAnimation(animation);

                    if (position != RecyclerView.NO_POSITION) {
                        UserGet userGet = userGetList.get(position);
                        model.deleteFriend(userGet);


                    }
                }
            });
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                UserGet userGet = userGetList.get(position);
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_animation);
                view.setAnimation(animation);
                Intent intent = new Intent(view.getContext(), Chat.class);
                intent.putExtra("displayName", userGet.getUser().getDisplayName());
                intent.putExtra("profilePic", userGet.getUser().getProfilePic());
                intent.putExtra("userId", userGet.getId());
                intent.putExtra("friendusername", userGet.getUser().getUsername());

                intent.putExtra("username", username);
                view.getContext().startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                UserGet userGet = userGetList.get(position);
                model.deleteFriend(userGet);

            }
            return true;
        }
    }

    /** fixtime
     * make the time on format of time and without date
     * @param time the time string from the server
     * @return the time on format of time and date
     */
    public static String fixtime(String time) {
        String createdTimestamp = time; // Replace with your timestamp

        // Define the input format of the timestamp
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());

        // Define the output format for time and date
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy", Locale.getDefault());

        try {
            // Parse the timestamp into a Date object
            Date date = inputFormat.parse(createdTimestamp);

            // Format the time and date using the output format
            String formattedDateTime = outputFormat.format(date);

            // Print the formatted time and date
            System.out.println("Formatted DateTime: " + formattedDateTime);

            // You can now use the formattedDateTime string as needed
            // ...

            return formattedDateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdTimestamp;
    }

    /**
     * fixtimewithdata - make the time on format of time with date
     * @param time the time string from the server
     * @return the time on format of time with date
     */
    public static String fixtimewithdata(String time) {
        String createdTimestamp = time; // Replace with your timestamp

        // Define the input format of the timestamp
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());

        // Define the output format for time only
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            // Parse the timestamp into a Date object
            Date date = inputFormat.parse(createdTimestamp);

            // Format the time using the output format
            String formattedTime = outputFormat.format(date);

            // Print the formatted time
            System.out.println("Formatted Time: " + formattedTime);

            // You can now use the formattedTime string as needed
            // ...

            return formattedTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdTimestamp;
    }



}
