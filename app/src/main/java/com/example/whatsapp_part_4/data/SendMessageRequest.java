package com.example.whatsapp_part_4.data;

//TODO JAVADOC
public class SendMessageRequest {
    private String msg;

    public SendMessageRequest(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}