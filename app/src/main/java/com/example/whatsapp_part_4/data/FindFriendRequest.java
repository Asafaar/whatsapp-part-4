package com.example.whatsapp_part_4.data;

/**
 * FindFriendRequest represents a request to find a friend based on a username.
 * It encapsulates the username of the friend being searched for.
 */
public class FindFriendRequest {
    private String username;

    /**
     * Constructs a new FindFriendRequest object with the specified username.
     *
     * @param username The username of the friend being searched for.
     */
    public FindFriendRequest(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the friend being searched for.
     *
     * @return The username of the friend.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the friend being searched for.
     *
     * @param username The username of the friend.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}