package com.example.whatsapp_part_4.data;

/**
 * The DataUserRes class represents user data retrieved from a data source.
 * It encapsulates the username, display name, and profile picture of a user.
 */
public class DataUserRes {
    private String username;
    private String displayName;
    private String profilePic;

    /**
     * Constructs a new DataUserRes object with the specified username, display name, and profile picture.
     *
     * @param username     The username of the user.
     * @param displayName  The display name of the user.
     * @param profilePic   The URL of the user's profile picture.
     */
    public DataUserRes(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    /**
     * Returns the URL of the user's profile picture.
     *
     * @return The profile picture URL.
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Returns the display name of the user.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }
}