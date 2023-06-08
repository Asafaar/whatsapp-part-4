package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Message {
    @PrimaryKey
    @NonNull
    private String id;
    private Sender sender;
    private String content;
    private String created;

    public Message(String id, Sender sender, String content, String created){
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.created = created;
    }
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
        // Getters and setters
    }



    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public Sender getSender() {
        return sender;
    }
}