package com.example.whatsapp_part_4.data;

/**
 * Represents a request to send a message.
 */
public class SendMessageRequest {
    private String msg;

    /**
     * Constructs a new instance of the SendMessageRequest class with the specified message.
     *
     * @param msg The message to be sent.
     */
    public SendMessageRequest(String msg) {
        this.msg = msg;
    }

    /**
     * Gets the message to be sent.
     *
     * @return The message.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the message to be sent.
     *
     * @param msg The message to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
