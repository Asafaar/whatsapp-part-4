package com.example.whatsapp_part_4.Adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;

import com.example.whatsapp_part_4.Activty.Chat;
import com.example.whatsapp_part_4.Activty.friends;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.UserGet;

import java.util.List;

public class UserGetAdapter extends RecyclerView.Adapter<UserGetAdapter.UserGetViewHolder> implements View.OnClickListener {
    private List<UserGet> userGetList;

    public UserGetAdapter(List<UserGet> userGetList) {
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
            holder.lastMessageTextView.setText("No message");
            holder.lastMessagetimeTextView.setText("");
            return;
        } else {
            holder.lastMessageTextView.setText(userGet.getLastMessage().getContent());
            holder.lastMessagetimeTextView.setText(userGet.getLastMessage().getCreated());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("UserGetAdapter", "onClick");
                UserGet userGet = userGetList.get(holder.getAdapterPosition());

                Intent intent = new Intent(view.getContext(), Chat.class);
                intent.putExtra("displayName", userGet.getUser().getDisplayName());
                intent.putExtra("profilePic", userGet.getUser().getProfilePic());
                intent.putExtra("userId", userGet.getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userGetList.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class UserGetViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePicImageView;
        public TextView displayNameTextView;
        public TextView lastMessageTextView;
        public TextView lastMessagetimeTextView;
        private int position;

        public UserGetViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            this.position = position;
            profilePicImageView = itemView.findViewById(R.id.imagefrined);
            displayNameTextView = itemView.findViewById(R.id.displayname);
            lastMessageTextView = itemView.findViewById(R.id.lastmsg);
            lastMessagetimeTextView = itemView.findViewById(R.id.lastmsgsend);


        }
    }
}