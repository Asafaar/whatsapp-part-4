package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Base64;
import java.util.UUID;

/**
 * Represents a User entity in the database.
 */
@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String id;
    private String username;
    private String displayName;
    private byte[] profilePic;

    /**
     * Constructs a new User object with the specified parameters.
     *
     * @param id               The ID of the user.
     * @param username         The username of the user.
     * @param displayName      The display name of the user.
     * @param base64ProfilePic The base64 encoded profile picture of the user.
     */
    public User(String id, String username, String displayName, String base64ProfilePic) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = Base64.getDecoder().decode(base64ProfilePic);
    }

    /**
     * Retrieves the profile picture of the user.
     *
     * @return The profile picture as a byte array.
     */
    public byte[] getProfilePic() {
        return profilePic;
    }

    /**
     * Retrieves the display name of the user.
     *
     * @return The display name of the user.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return The ID of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the display name of the user.
     *
     * @param displayName The new display name of the user.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The new ID of the user.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the profile picture of the user.
     *
     * @param profilePic The new profile picture as a byte array.
     */
    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
