package com.example.whatsapp_part_4.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Activty.Chat;
import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;

import java.util.List;

public class UserGetAdapter extends RecyclerView.Adapter<UserGetAdapter.UserGetViewHolder>  {
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
            holder.lastMessageTextView.setText("No message");
            holder.lastMessagetimeTextView.setText("");
            return;
        } else {
            holder.lastMessageTextView.setText(userGet.getLastMessage().getContent());
            holder.lastMessagetimeTextView.setText(userGet.getLastMessage().getCreated());
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("UserGetAdapter", "onClick");
//                int position = holder.getAdapterPosition();
//                Log.e("TAG", "onClick: "+position );
//                UserGet userGet = userGetList.get(position);
//
//                Intent intent = new Intent(view.getContext(), Chat.class);
//                intent.putExtra("displayName", userGet.getUser().getDisplayName());
//                intent.putExtra("profilePic", userGet.getUser().getProfilePic());
//                intent.putExtra("userId", userGet.getId());
//                view.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return userGetList.size();
    }



    public  class UserGetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public ImageView profilePicImageView;
        public TextView displayNameTextView;
        public TextView lastMessageTextView;
        public TextView lastMessagetimeTextView;
        public Button deleteButton;
        private int position;

        Model model;

        public UserGetViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            this.position = position;
             model = DatabaseSingleton.getModel(itemView.getContext());
            profilePicImageView = itemView.findViewById(R.id.imagefrined);
            displayNameTextView = itemView.findViewById(R.id.displayname);
            lastMessageTextView = itemView.findViewById(R.id.lastmsg);
            lastMessagetimeTextView = itemView.findViewById(R.id.lastmsgsend);
            deleteButton=itemView.findViewById(R.id.deletefriend);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
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
}