package com.example.whatsapp_part_4.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.whatsapp_part_4.data.ConstantData;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * UserGetAdapter is a RecyclerView adapter that displays a list of UserGet items.
 */
public class UserGetAdapter extends RecyclerView.Adapter<UserGetAdapter.UserGetViewHolder> {
    private List<UserGet> userGetList;
    private String username;

    /**
     * Constructs a UserGetAdapter with the specified list of UserGet items and username.
     *
     * @param userGetList The list of UserGet items to display.
     * @param username    The username.
     */
    public UserGetAdapter(List<UserGet> userGetList, String username) {
        this.userGetList = userGetList;
        this.username = username;
    }

    /**
     * Sets the list of UserGet items to display.
     *
     * @param userGetList The list of UserGet items.
     */
    public void setUserGetList(List<UserGet> userGetList) {
        this.userGetList = userGetList;
    }

    @NonNull
    @Override
    public UserGetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new UserGetViewHolder(view);
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
        } else {
            holder.lastMessageTextView.setText(userGet.getLastMessage().getContent());
            holder.lastMessageTimeTextView.setText(fixTime(userGet.getLastMessage().getCreated()));
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
        public TextView lastMessageTimeTextView;
        public ImageView deleteButton;

        Model model;

        /**
         * Constructs a UserGetViewHolder with the specified itemView and position.
         *
         * @param itemView The itemView for the UserGetViewHolder.
         */
        public UserGetViewHolder(@NonNull View itemView) {
            super(itemView);
            model = DatabaseSingleton.getModel(itemView.getContext());
            profilePicImageView = itemView.findViewById(R.id.imagefriend);
            displayNameTextView = itemView.findViewById(R.id.displayname);
            lastMessageTextView = itemView.findViewById(R.id.lastmsg);
            lastMessageTimeTextView = itemView.findViewById(R.id.timestamp);
            deleteButton = itemView.findViewById(R.id.deletefriend);
            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.open_trash_can);
                view.startAnimation(animation);

                if (position != RecyclerView.NO_POSITION) {
                    UserGet userGet = userGetList.get(position);
                    model.deleteFriend(userGet);
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
                intent.putExtra("friendUserName", userGet.getUser().getUsername());
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

    /**
     * Converts the time string from the server into a formatted time with date.
     *
     * @param time The time string from the server.
     * @return The formatted time with date.
     */
    public static String fixTime(String time) {

        // Define the input format of the timestamp
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());

        // Define the output format for time and date
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy", Locale.getDefault());

        try {
            // Parse the timestamp into a Date object
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = inputFormat.parse(time);

            // Format the time and date using the output format
            String formattedDateTime = outputFormat.format(date);

            outputFormat.setTimeZone(TimeZone.getTimeZone(ConstantData.TimeZoneId));

            return formattedDateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Converts the time string from the server into a formatted time without date.
     *
     * @param time The time string from the server.
     * @return The formatted time without date.
     */
    public static String fixTimeWithData(String time) {

        // Define the input format of the timestamp
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());

        // Define the output format for time only
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            // Parse the timestamp into a Date object
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = inputFormat.parse(time);

            // Format the time using the output format
            String formattedTime = outputFormat.format(date);

            outputFormat.setTimeZone(TimeZone.getTimeZone(ConstantData.TimeZoneId));

            // Print the formatted time
            System.out.println("Formatted Time: " + formattedTime);

            // You can now use the formattedTime string as needed
            // ...

            return formattedTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
}
