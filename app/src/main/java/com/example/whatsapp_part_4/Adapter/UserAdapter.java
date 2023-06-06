package com.example.whatsapp_part_4.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.displayNameTextView.setText(user.getDisplayName());

//        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getProfilePic(), 0, user.getProfilePic().length);
//        holder.imageuser.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView lastmsg;
        public TextView lastmsgsend;//TODO: add last message send
        public ImageView imageuser;
        public TextView displayNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            displayNameTextView = itemView.findViewById(R.id.displayname);
            imageuser = itemView.findViewById(R.id.imagefrined);
        }
    }
}