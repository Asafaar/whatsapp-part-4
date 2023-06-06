package com.example.whatsapp_part_4.data;

public class SendMsgRequest {
    private String msg;
    private String username;

    private String displayName;
    private byte[] profilePic;

    public SendMsgRequest(String msg, String username, String displayName, byte[] profilePic) {
        this.msg = msg;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }
}
