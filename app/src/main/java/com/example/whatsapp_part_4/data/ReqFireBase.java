package com.example.whatsapp_part_4.data;

/**
 * Represents a Firebase token request.
 */
public class ReqFireBase {
    private String token;
    private String username;

    /**
     * Constructs a new ReqFireBase object with the given token and username.
     *
     * @param token    The Firebase token.
     * @param username The username associated with the token.
     */
    public ReqFireBase(String token, String username) {
        this.token = token;
        this.username = username;
    }

    /**
     * Returns the username associated with the Firebase token.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username associated with the Firebase token.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}