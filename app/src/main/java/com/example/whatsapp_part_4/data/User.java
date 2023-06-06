package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Base64;
import java.util.UUID;

@Entity
public class User {

    @NonNull
    @PrimaryKey
    private String id;
    private String username;
    private String displayName;
    private byte[] profilePic;

    public User(String id,String username, String displayName, String base64ProfilePic ){
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.username = username;
        this.displayName = displayName;
        this.profilePic = Base64.getDecoder().decode(base64ProfilePic);

        this.id = id;
    }
    User(){}

    public byte[] getProfilePic() {
        return profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
