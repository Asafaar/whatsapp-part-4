package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a UserGet entity in the database.
 */
@Entity
public class UserGet {
    @NonNull
    @PrimaryKey
    private String id;
    private User user;
    private final Message lastMessage;

    /**
     * Constructs a new UserGet object with the specified parameters.
     *
     * @param id          The ID of the UserGet.
     * @param user        The User object associated with the UserGet.
     * @param lastMessage The last message associated with the UserGet.
     */
    public UserGet(String id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    /**
     * Retrieves the ID of the UserGet.
     *
     * @return The ID of the UserGet.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the UserGet.
     *
     * @param id The new ID of the UserGet.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the User object associated with the UserGet.
     *
     * @return The User object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the User object associated with the UserGet.
     *
     * @param user The new User object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the last message associated with the UserGet.
     *
     * @return The last message.
     */
    public Message getLastMessage() {
        return lastMessage;
    }

    /**
     * Represents a User entity in the UserGet object.
     */
    public static class User {
        private String username;
        private String displayName;
        private String profilePic;

        /**
         * Constructs a new User object with the specified parameters.
         *
         * @param username    The username of the user.
         * @param displayName The display name of the user.
         * @param profilePic  The profile picture of the user.
         */
        public User(String username, String displayName, String profilePic) {
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
        }

        /**
         * Retrieves the username of the user.
         *
         * @return The username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username of the user.
         *
         * @param username The new username.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Retrieves the display name of the user.
         *
         * @return The display name.
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Sets the display name of the user.
         *
         * @param displayName The new display name.
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        /**
         * Retrieves the profile picture of the user.
         *
         * @return The profile picture.
         */
        public String getProfilePic() {
            return profilePic;
        }

        /**
         * Sets the profile picture of the user.
         *
         * @param profilePic The new profile picture.
         */
        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }

    /**
     * Represents a Message entity in the UserGet object.
     */
    public static class Message {
        private String id;
        private final String created;
        private String content;

        /**
         * Constructs a new Message object with the specified parameters.
         *
         * @param id       The ID of the message.
         * @param created  The timestamp when the message was created.
         * @param content  The content of the message.
         */
        public Message(String id, String created, String content) {
            this.id = id;
            this.created = created;
            this.content = content;
        }

        /**
         * Retrieves the ID of the message.
         *
         * @return The ID of the message.
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the ID of the message.
         *
         * @param id The new ID of the message.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Retrieves the timestamp when the message was created.
         *
         * @return The creation timestamp.
         */
        public String getCreated() {
            return created;
        }

        /**
         * Retrieves the content of the message.
         *
         * @return The message content.
         */
        public String getContent() {
            return content;
        }

        /**
         * Sets the content of the message.
         *
         * @param content The new message content.
         */
        public void setContent(String content) {
            this.content = content;
        }
    }
}
