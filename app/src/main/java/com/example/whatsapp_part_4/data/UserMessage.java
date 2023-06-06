package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(tableName = "user_messages",
//        primaryKeys = {"userId", "messageId"}
//        ,
////        foreignKeys = {
////                @ForeignKey(entity = User.class,
////                        parentColumns = "id",
////                        childColumns = "userId"),
////                @ForeignKey(entity = Message.class,
////                        parentColumns = "id",
////                        childColumns = "messageId")
////        },
////        indices = {
////                @Index("userId"),
////                @Index("messageId")
////        })
@Entity(tableName = "user_messages", primaryKeys = {"userId", "messageId"})
public class UserMessage {
    @NonNull
    public String userId;
    @NonNull
    public String messageId;

    public String getMessageId() {
        return messageId;
    }

    public String getUserId() {
        return userId;
    }

    UserMessage(@NonNull String userId, @NonNull String messageId){
        this.userId = userId;
        this.messageId = messageId;
    }
}