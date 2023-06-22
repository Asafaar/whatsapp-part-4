package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * LastMessageByUser represents the last message sent by a user.
 * It is an entity class mapped to a Room database table.
 */
@Entity
public class LastMessageByUser {
    @PrimaryKey
    @NonNull
    String id;
    Message msgId;

    /**
     * Returns the ID of the last message by the user.
     *
     * @return The ID of the last message.
     */
    public String getId() {
        return id;
    }
}
