package com.example.whatsapp_part_4.data;

public class DataUserRes {
    String username;
    String displayName;
    String profilePic;
    public DataUserRes(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }
}
