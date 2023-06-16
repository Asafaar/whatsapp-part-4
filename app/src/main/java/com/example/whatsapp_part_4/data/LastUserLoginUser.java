package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * LastUserLoginUser is an entity class representing the user's last login information.
 */
@Entity
public class LastUserLoginUser {

    @PrimaryKey
    @NonNull
    String username;

    /**
     * Sets the username for the user's last login.
     *
     * @param username The username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username for the user's last login.
     *
     * @return The username of the user's last login.
     */
    public String getUsername() {
        return username;
    }
}
