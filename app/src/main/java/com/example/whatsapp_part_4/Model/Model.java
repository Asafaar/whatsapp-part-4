package com.example.whatsapp_part_4.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DataUserRes;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.Repository;
import com.example.whatsapp_part_4.data.ThemeString;
import com.example.whatsapp_part_4.data.UserGet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The Model class represents the data and business logic of the application.
 */
public class Model {
    private final LiveData<List<Message>> messages; // Local data messages that will be displayed on the screen
    private final LiveData<List<UserGet>> usersGet; // Local data for friends that will be displayed on the screen
    private final Repository repository;

    /**
     * Constructs a Model object.
     *
     * @param db The Appdb object for database operations.
     */
    public Model(Appdb db) {
        this.repository = new Repository(db, this);
        messages = repository.getListMessages();
        usersGet = repository.getListUsersGet();
    }

    /**
     * Reloads the users from the database and the server to LiveData.
     */
    public void reload() {
        repository.reloadUsers();
    }

    /**
     * Reloads the users in the background.
     */
    public void reloadUsersInTheBack() {
        repository.reloadUsersInTheBack();
    }

    /**
     * Reloads the UserGet from the database.
     */
    public void reloadUserGetFromDb() {
        repository.reloadUserGetFromDb();
    }

    /**
     * Sends a message to the server.
     *
     * @param idOfFriend     The ID of the friend.
     * @param msg            The message.
     * @param friendUsername The username of the friend.
     */
    public synchronized void sendMessage(String idOfFriend, String msg, String friendUsername) {
        int status = repository.sendMessage(idOfFriend, msg, friendUsername);
        if (status == 1) {
            Log.i("TAG", "sendMessage: success");
        } else {
            Log.e("TAG", "sendMessage: fail");
        }
    }

    /**
     * Gets the current theme.
     *
     * @return The ThemeString object representing the current theme.
     */
    public ThemeString getTheme() {
        if (repository.getTheme() == null) {
            return null;
        } else {
            return repository.getTheme();
        }
    }

    /**
     * Sets the current theme.
     *
     * @param theme The theme value to be set.
     */
    public void setTheme(int theme) {
        repository.setTheme(theme);
    }

    /**
     * Gets the last user login.
     *
     * @return The last user login.
     */
    public String getLastUserLogin() {
        return repository.getLastUserLogin();
    }

    /**
     * Sets the last user login.
     *
     * @param username The username of the last logged-in user.
     */
    public void setLastUserLogin(String username) {
        repository.setLastUserLogin(username);
    }

    /**
     * Retrieves the user data from the server.
     *
     * @param username The username of the user.
     * @return A CompletableFuture that resolves to the DataUserRes object representing the user data.
     */
    public CompletableFuture<DataUserRes> getUserData(String username) {
        CompletableFuture<DataUserRes> future = repository.getUserData(username);

        future.thenAccept(userData -> {
            if (userData != null) {
                Log.d("model", "Received object: " + userData);
            } else {
                Log.d("model", "Received object is null");
            }
        });

        return future;
    }

    /**
     * Sets the Retrofit URL for API calls.
     *
     * @param url The base URL of the Retrofit service.
     */
    public void setRetrofit(String url) {
        repository.setRetrofit(url);
    }

    /**
     * Attempts to log in to the server.
     *
     * @param username The username.
     * @param password The password.
     * @return A CompletableFuture that resolves to an Integer representing the login status.
     */
    public synchronized CompletableFuture<Integer> tryLogin(String username, String password) {
        return repository.tryLogin(username, password);
    }

    /**
     * Creates a new user on the server.
     *
     * @param username    The username.
     * @param password    The password.
     * @param displayName The display name.
     * @param profilePic  The profile picture.
     * @return A CompletableFuture that resolves to an Integer representing the status of the user creation.
     */
    public CompletableFuture<Integer> makeNewUser(String username, String password, String displayName, String profilePic) {
        return repository.MakeNewUser(username, password, displayName, profilePic);
    }

    /**
     * Deletes a friend from the server, database, and LiveData.
     *
     * @param user The UserGet object representing the friend to be deleted.
     */
    public synchronized void deleteFriend(UserGet user) {
        repository.deleteFriend(user);
    }

    /**
     * Adds a new friend to the server by searching if the friend is in the server.
     *
     * @param friend The username of the friend to be added.
     * @return A CompletableFuture that resolves to an Integer representing the status of adding the friend.
     */
    public synchronized CompletableFuture<Integer> addNewFriend(String friend) {
        return repository.addNewFriend(friend);
    }

    /**
     * Sends the delete token to Firebase.
     *
     * @param username The username.
     */
    public void sendTokenFireBaseDel(String username) {
        repository.sendTokenFireBaseDel(username);
    }

    /**
     * Registers the Firebase token to the server.
     *
     * @param token    The Firebase token.
     * @param username The username.
     * @return A CompletableFuture that resolves to an Integer representing the registration status.
     */
    public CompletableFuture<Integer> registerFireBase(String token, String username) {
        return repository.registerFireBase(token, username);
    }

    /**
     * Adds a message to LiveData when the user sends a message to a friend.
     *
     * @param message The Message object representing the sent message.
     */
    public void addMessage(Message message) {
        repository.addMessage(message);
    }

    /**
     * Gets the Firebase token from the repository.
     *
     * @return The Firebase token.
     */
    public String getToken() {
        return repository.getToken();
    }

    /**
     * Gets the messages LiveData.
     *
     * @return The LiveData object representing the messages.
     */
    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    /**
     * Gets the usersGet LiveData.
     *
     * @return The LiveData object representing the usersGet.
     */
    public LiveData<List<UserGet>> getUsersGet() {
        return this.usersGet;
    }

    /**
     * Clears the logout user from the repository.
     */
    public void clearLogoutUser() {
        repository.clearLogoutUser();
    }
}
