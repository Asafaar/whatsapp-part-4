package com.example.whatsapp_part_4.Adapter;

import static com.example.whatsapp_part_4.Adapter.UserGetAdapter.fixtime;
import static com.example.whatsapp_part_4.Adapter.UserGetAdapter.fixtimewithdata;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

         if (viewType == USER_SENT) {
             // Inflate the layout for the user-sent message
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recevier_item, parent, false);
             params.gravity = Gravity.END;
             view.setLayoutParams(params);
         } else {
             // Inflate the layout for the friend-received message
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_item, parent, false);
             params.gravity = Gravity.START ;
             view.setLayoutParams(params);
         }
         return new MessageViewHolder(view);
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
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
         RecyclerView.LayoutParams params2 = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.WRAP_CONTENT);
         if (message.getSender().getUsername().equals(friends.username)) {
             // User-sent message on the right
             Log.e("TAG", "onBindViewHolder: "+message.getSender().getUsername() );
             params.gravity = Gravity.END;
                holder.linearLayout.setLayoutParams(params);
                holder.linearLayout.setGravity(Gravity.END);
                holder.contentTextView.setGravity(Gravity.END);
                holder.createdTextView.setGravity(Gravity.END);
         } else {
             // Friend-received message on the left
             params.gravity = Gravity.START ;
             holder.linearLayout.setLayoutParams(params);
             holder.linearLayout.setGravity(Gravity.START);
             holder.contentTextView.setGravity(Gravity.START);
             holder.createdTextView.setGravity(Gravity.START);

         }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTextView;
        public TextView createdTextView;
        public LinearLayout linearLayout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.message);
            createdTextView = itemView.findViewById(R.id.timestamp);
            linearLayout = itemView.findViewById(R.id.layout);

        }
    }
}