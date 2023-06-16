package com.example.whatsapp_part_4.Adapter;

import static com.example.whatsapp_part_4.Adapter.UserGetAdapter.fixtimewithdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Activty.Friends;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Message;

import java.util.List;

/**
 * The MessageAdapter class is responsible for displaying message objects in a RecyclerView.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    public static final int USER_SENT = 1;
    public static final int FRIEND_RECEIVED = 2;
    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Sets the list of messages to be displayed.
     *
     * @param messages The list of messages.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        // Inflate the appropriate layout based on the viewType
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_item, parent, false);
        return new MessageViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().getUsername().equals(Friends.username)) {
            // The message was sent by the user
            return USER_SENT;
        } else {
            // The message was received from a friend
            return FRIEND_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.contentTextView.setText(message.getContent());
        holder.createdTextView.setText(fixtimewithdata(message.getCreated()));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.linearLayout.getLayoutParams();
        if (message.getSender().getUsername().equals(Friends.username)) {
            // User-sent message on the right
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.removeRule(RelativeLayout.ALIGN_PARENT_END);
            holder.linearLayout.setBackgroundResource(R.drawable.sender_bubble);
        } else {
            // Friend-received message on the left
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            params.removeRule(RelativeLayout.ALIGN_PARENT_START);
            holder.linearLayout.setBackgroundResource(R.drawable.receiver_bubble);
        }

        holder.linearLayout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTextView;
        public TextView createdTextView;
        public LinearLayout linearLayout;

        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.message);
            createdTextView = itemView.findViewById(R.id.timestamp);
            linearLayout = itemView.findViewById(R.id.layout);
        }
    }
}
