package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class UserGet {
    @NonNull
    @PrimaryKey
    private String id;
    private User user;
    private Message lastMessage;

    public UserGet(String id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public static class User {
        private String username;
        private String displayName;
        private String profilePic;

        public User(String username, String displayName, String profilePic) {
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
        }

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

    public static class Message {
        private String id;
        private String created;
        private String content;

        public Message(String id, String created, String content) {
            this.id = id;
            this.created = created;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public String getCreated() {
            return created;
        }

        public String getContent() {
            return content;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}