package com.example.whatsapp_part_4.data;
public class FindFriendRequest {
    private String username;

    public FindFriendRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}