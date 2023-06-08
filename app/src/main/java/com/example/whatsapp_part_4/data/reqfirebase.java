package com.example.whatsapp_part_4.data;

public class reqfirebase {
    String token;
    String username;
    reqfirebase(String token,String username){
        this.token=token;
        this.username=username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

}
