package com.example.whatsapp_part_4.data;

public class UserRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public UserRequest(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
}
