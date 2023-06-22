package com.example.whatsapp_part_4.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * ChatEn is an entity class representing a chat conversation in the WhatsApp application.
 * It contains information about the chat's ID, participants (users), and messages.
 */
@Entity
public class ChatEn {

    @PrimaryKey
    private int id;
    private List<User> users;
    private List<Message> messages;

    /**
     * Retrieves the ID of the chat conversation.
     *
     * @return The ID of the chat.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the chat conversation.
     *
     * @param id The ID of the chat.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the list of users participating in the chat.
     *
     * @return The list of User objects representing the participants.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users participating in the chat.
     *
     * @param users The list of User objects representing the participants.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Retrieves the list of messages in the chat.
     *
     * @return The list of Message objects representing the chat messages.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Sets the list of messages in the chat.
     *
     * @param messages The list of Message objects representing the chat messages.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * User is a nested class representing a user participating in a chat conversation.
     * It contains information about the user's username, display name, and profile picture.
     */
    public static class User {
        private String username;
        private String displayName;
        private String profilePic;

        /**
         * Retrieves the username of the user.
         *
         * @return The username of the user.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username of the user.
         *
         * @param username The username of the user.
         */
        public void setUsername(String username) {
            this.username = username;
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
         * Sets the display name of the user.
         *
         * @param displayName The display name of the user.
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        /**
         * Retrieves the profile picture of the user.
         *
         * @return The profile picture of the user.
         */
        public String getProfilePic() {
            return profilePic;
        }

        /**
         * Sets the profile picture of the user.
         *
         * @param profilePic The profile picture of the user.
         */
        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }
    }

    /**
     * Message is a nested class representing a message within a chat conversation.
     * It contains information about the message's ID, creation timestamp, sender, and content.
     */
    public static class Message {
        private int id;
        private String created;
        private User sender;
        private String content;

        /**
         * Retrieves the ID of the message.
         *
         * @return The ID of the message.
         */
        public int getId() {
            return id;
        }

        /**
         * Sets the ID of the message.
         *
         * @param id The ID of the message.
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * Retrieves the creation timestamp of the message.
         *
         * @return The creation timestamp of the message.
         */
        public String getCreated() {
            return created;
        }

        /**
         * Sets the creation timestamp of the message.
         *
         * @param created The creation timestamp of the message.
         */
        public void setCreated(String created) {
            this.created = created;
        }

        /**
         * Retrieves the sender of the message.
         *
         * @return The User object representing the message sender.
         */
        public User getSender() {
            return sender;
        }

        /**
         * Sets the sender of the message.
         *
         * @param sender The User object representing the message sender.
         */
        public void setSender(User sender) {
            this.sender = sender;
        }

        /**
         * Retrieves the content of the message.
         *
         * @return The content of the message.
         */
        public String getContent() {
            return content;
        }

        /**
         * Sets the content of the message.
         *
         * @param content The content of the message.
         */
        public void setContent(String content) {
            this.content = content;
        }
    }
}
