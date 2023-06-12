package com.example.whatsapp_part_4.Adapter;

import static com.example.whatsapp_part_4.Adapter.UserGetAdapter.fixtime;
import static com.example.whatsapp_part_4.Adapter.UserGetAdapter.fixtimewithdata;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Activty.friends;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.Message;

import java.util.List;
//TODO make the message more beautiful
 /** MessageAdapter-get message object and set the message in the view
 *
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;
     public static final int USER_SENT = 1;
     public static final int FRIEND_RECEIVED = 2;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

     @NonNull
     @Override
     public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view;
//         int layoutId = (viewType == USER_SENT) ? R.layout.sender_item : R.layout.recevier_item;

         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_item, parent, false);

//         if (viewType == USER_SENT) {
//             // Inflate the layout for the user-sent message
//             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recevier_item, parent, false);
//             params.gravity = Gravity.END;
//             view.setLayoutParams(params);
//         } else {
//             // Inflate the layout for the friend-received message
//             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_item, parent, false);
//             params.gravity = Gravity.START ;
//             view.setLayoutParams(params);
//         }
         return new MessageViewHolder(view,viewType);
     }
     @Override
     public int getItemViewType(int position) {
         Message message = messages.get(position);
         if (message.getSender().getUsername().equals(friends.username)) {
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
         if (message.getSender().getUsername().equals(friends.username)) {
             // User-sent message on the right
             params.addRule(RelativeLayout.ALIGN_PARENT_END);
             params.removeRule(RelativeLayout.ALIGN_PARENT_START);
             holder.linearLayout.setBackgroundResource(R.drawable.sender_bubble);
         } else {
             // Friend-received message on the left
             params.addRule(RelativeLayout.ALIGN_PARENT_START);
             params.removeRule(RelativeLayout.ALIGN_PARENT_END);
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

        public MessageViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.message);
            createdTextView = itemView.findViewById(R.id.timestamp);
            linearLayout = itemView.findViewById(R.id.layout);
            if (viewType == USER_SENT) {
                linearLayout.setBackgroundResource(R.drawable.sender_bubble2);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
                params.addRule(RelativeLayout.END_OF, 0);
                linearLayout.setLayoutParams(params);
            } else {
                linearLayout.setBackgroundResource(R.drawable.receiver_bubble2);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
                params.addRule(RelativeLayout.END_OF, 0);
                linearLayout.setLayoutParams(params);
            }
        }
    }
}