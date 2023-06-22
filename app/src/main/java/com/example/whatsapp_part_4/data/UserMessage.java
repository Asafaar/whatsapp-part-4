package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Represents a UserMessage entity in the database.
 */
@Entity(tableName = "user_messages", primaryKeys = {"userId", "messageId"})
public class UserMessage {
    @NonNull
    public String userId;
    @NonNull
    public String messageId;

    /**
     * Constructs a new UserMessage object with the specified parameters.
     *
     * @param userId    The ID of the user associated with the message.
     * @param messageId The ID of the message.
     */
    public UserMessage(@NonNull String userId, @NonNull String messageId) {
        this.userId = userId;
        this.messageId = messageId;
    }
}