package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a message in the application.
 */
@Entity
public class Message {
    @PrimaryKey
    @NonNull
    private String id;
    private Sender sender;
    private String content;
    private String created;

    /**
     * Constructs a new Message object.
     *
     * @param id       The ID of the message.
     * @param sender   The sender of the message.
     * @param content  The content of the message.
     * @param created  The timestamp of when the message was created.
     */
    public Message(String id, Sender sender, String content, String created) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.created = created;
    }

    /**
     * Represents the sender of a message.
     */
    public static class Sender {
        private String username;
        private String displayName;
        private String profilePic;

        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }

    /**
     * Returns the content of the message.
     *
     * @return The content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the ID of the message.
     *
     * @return The ID of the message.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the timestamp of when the message was created.
     *
     * @return The timestamp of when the message was created.
     */
    public String getCreated() {
        return created;
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public Sender getSender() {
        return sender;
    }
}
