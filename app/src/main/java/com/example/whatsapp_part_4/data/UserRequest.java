package com.example.whatsapp_part_4.data;

/**
 * Represents a user request object.
 */
public class UserRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    /**
     * Constructs a UserRequest object with the specified values.
     *
     * @param username    The username of the user.
     * @param password    The password of the user.
     * @param displayName The display name of the user.
     * @param profilePic  The profile picture of the user.
     */
    public UserRequest(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the display name of the user.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of the user.
     *
     * @param displayName The display name to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the profile picture of the user.
     *
     * @return The profile picture.
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the profile picture of the user.
     *
     * @param profilePic The profile picture to set.
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
