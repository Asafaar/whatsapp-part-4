package com.example.whatsapp_part_4.data;

/**
 * LoginRequest is a class that represents a login request.
 * It contains the username and password for authentication.
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructs a new instance of LoginRequest with the specified username and password.
     *
     * @param username The username for the login request.
     * @param password The password for the login request.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username associated with the login request.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password associated with the login request.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }
}