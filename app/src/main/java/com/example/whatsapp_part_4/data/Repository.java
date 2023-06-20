package com.example.whatsapp_part_4.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_part_4.Async.AsyncTaskUsers;
import com.example.whatsapp_part_4.Model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Repository {
    private final MessageDao messageDao;
    private ListMessage listMessages;

    private String token;
    private final ListUsers listusers;
    private final ListUserGet listUserGets;
    private final MainApiManger mainApiManger;
    private final UserMessageConnectDao userMessageConnectDao;
    private final LastMsgByuser lastMsgByuser;
    private final UeserGet UserGet;
    private final ThemeSave themeSaveDb;
    private final LastUserLogin lastUserLogin;
    private String username;
    private String displayName;
    private String profilePic;
    private final Model model;

    /**
     * Creates a new Repository object.
     *
     * @param db    The Appdb object for database access.
     * @param model The Model object.
     */
    public Repository(Appdb db, Model model) {
        this.model = model;
        messageDao = db.messageDao();
        userMessageConnectDao = db.userMessageConnectDao();
        lastMsgByuser = db.lastMsgByuser();
        UserGet = db.UeserGet();
        themeSaveDb = db.ThemeSave();
        lastUserLogin = db.LastUserLogin();
        listusers = new ListUsers();
        listMessages = new ListMessage();
        listUserGets = new ListUserGet();
        mainApiManger = new MainApiManger(null, UserGet);
    }

    /**
     * Returns the MutableLiveData object for list of messages.
     *
     * @return The MutableLiveData object containing the list of messages.
     */
    public MutableLiveData<List<Message>> getListMessages() {
        return listMessages;
    }

    /**
     * Sends a message to a friend.
     *
     * @param idOfFriend     The ID of the friend.
     * @param msg            The message to send.
     * @param friendUserName The username of the friend.
     * @return The result of sending the message (1 if successful, -1 otherwise).
     */
    public int sendMessage(String idOfFriend, String msg, String friendUserName) {
        CompletableFuture<Message> future = mainApiManger.sendMessage(idOfFriend, msg);
        future.thenCompose(message -> {
            if (message != null) {
                List<Message> myDataList = listMessages.getValue();
                assert myDataList != null;
                myDataList.add(0, message);
                listMessages.setValue(myDataList);
                CompletableFuture<Integer> completableFuture = mainApiManger.sendMessageWithFirebase(message, friendUserName);
                return completableFuture.thenApply(integer -> {
                    if (integer == 200) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
            } else {
                return CompletableFuture.completedFuture(-1);
            }
        }).exceptionally(throwable -> -1);
        return 1;
    }

    /**
     * Sets the Retrofit base URL.
     *
     * @param url The base URL to set.
     */
    public void setRetrofit(String url) {
        mainApiManger.setRetrofit(url);
    }

    /**
     * Retrieves the token.
     *
     * @return The token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Retrieves the theme.
     *
     * @return The theme string.
     */
    public ThemeString getTheme() {
        if (this.themeSaveDb.getTheme() == null) {
            return null;
        } else {
            return this.themeSaveDb.getTheme();
        }
    }

    /**
     * Sets the theme.
     *
     * @param theme The theme to set.
     */
    public void setTheme(int theme) {
        this.themeSaveDb.deleteTheme();
        this.themeSaveDb.insertTheme(String.valueOf(theme));
    }

    /**
     * Retrieves the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the display name.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName The display name to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves the profile picture.
     *
     * @return The profile picture.
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the profile picture.
     *
     * @param profilePic The profile picture to set.
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Retrieves user data from the main API manager.
     *
     * @param username The username of the user.
     * @return A CompletableFuture containing the user data.
     */
    public CompletableFuture<DataUserRes> getUserData(String username) {
        CompletableFuture<DataUserRes> future = mainApiManger.getUserData(username);

        future.thenAccept(userData -> {
            if (userData != null) {
                Log.d("repository", "Received object: " + userData);
            } else {
                Log.d("repository", "Received object is null");
            }
        });

        return future;
    }

    /**
     * Returns the MutableLiveData object for the list of users.
     *
     * @return The MutableLiveData object containing the list of users.
     */
    public MutableLiveData<List<User>> getListUsers() {
        return listusers;
    }

    /**
     * Adds a new friend to the main API manager.
     *
     * @param friend The username of the friend to add.
     * @return A CompletableFuture containing the result code:
     * - 1: Success
     * - -2: Unauthorized
     * - -3: Bad Request
     * - -1: Unknown error
     */
    public synchronized CompletableFuture<Integer> addNewFriend(String friend) {
        return mainApiManger.AddFriend(friend)
                .thenCompose(statusCode -> {
                    if (statusCode == 200) {
                        return mainApiManger.getFriends()
                                .thenApply(response -> {
                                    List<UserGet> userGetList1 = UserGet.getAllUsers();
                                    listUserGets.postValue(userGetList1);
                                    return 1; // Success
                                });
                    } else if (statusCode == 401) {
                        return CompletableFuture.completedFuture(-2); // Unauthorized
                    } else if (statusCode == 400) {
                        return CompletableFuture.completedFuture(-3); // Bad Request
                    } else {
                        Log.e("TAG", "addNewFriend: -1");
                        return CompletableFuture.completedFuture(-1); // Unknown error
                    }
                })
                .exceptionally(throwable -> {
                    Log.e("TAG", "addNewFriend: -1");
                    return -1; // Unknown error
                });
    }

    /**
     * Adds a message to the list of messages.
     *
     * @param message The message to add.
     */
    public void addMessage(Message message) {
        List<Message> myDataList = listMessages.getValue();
        assert myDataList != null;
        myDataList.add(0, message);
        listMessages.postValue(myDataList);
    }

    /**
     * Registers the Firebase token for a user in the main API manager.
     *
     * @param token    The Firebase token.
     * @param username The username of the user.
     * @return A CompletableFuture containing the result code:
     * - 1: Success
     * - -1: Unknown error
     */
    public CompletableFuture<Integer> registerFireBase(String token, String username) {
        return mainApiManger.registerFireBase(token, username);
    }

    /**
     * Reloads the users by executing an AsyncTask.
     */
    public void reloadUsers() {
        AsyncTaskUsers messageLoader = new AsyncTaskUsers(model);
        messageLoader.execute();
    }

    /**
     * Reloads the users in the background using CompletableFuture.
     */
    public void reloadUsersInTheBack() {
        CompletableFuture<Integer> future = mainApiManger.getFriends();
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                List<UserGet> userGetList1 = UserGet.getAllUsers();
                listUserGets.postValue(userGetList1);
                return 1;
            } else {
                return -1;
            }
        });
    }

    /**
     * Attempts to log in a user using the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A CompletableFuture containing the result code:
     * - 1: Success
     * - Other values: Error codes based on the status code returned by the API
     */
    public CompletableFuture<Integer> tryLogin(String username, String password) {
        return mainApiManger.tryLogin(username, password);
    }

    /**
     * Makes a new user registration request with the provided details.
     *
     * @param username    The username of the new user.
     * @param password    The password of the new user.
     * @param displayName The display name of the new user.
     * @param profilePic  The profile picture of the new user.
     * @return A CompletableFuture containing the result code:
     * - 1: Success
     * - Other values: Error codes based on the status code returned by the API
     */
    public CompletableFuture<Integer> MakeNewUser(String username, String password, String displayName, String profilePic) {
        return mainApiManger.MakeNewUser(username, password, displayName, profilePic);
    }

    /**
     * Sends a Firebase token deletion request for the specified username.
     *
     * @param username The username for which the Firebase token should be deleted.
     */
    public void sendTokenFireBaseDel(String username) {
        CompletableFuture<Integer> future = mainApiManger.sendTokenFireBaseDel(username);
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    /**
     * Deletes a friend from the user's friend list.
     *
     * @param user The user to be deleted as a friend.
     * @return An integer indicating the result:
     * - 1: Success
     * - Other values: Error codes based on the status code returned by the API
     */
    public int deleteFriend(UserGet user) {
        CompletableFuture<Integer> future = mainApiManger.deleteFriend(user.getId());
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                List<String> list = userMessageConnectDao.getMessageIdsForUser(user.getId());
                for (String id : list) {
                    messageDao.deleteMessageById(id);
                }
                userMessageConnectDao.deleteMessagesForUser(user.getId());
                List<UserGet> myDataList = listUserGets.getValue();
                assert myDataList != null;
                myDataList.remove(user);
                listUserGets.setValue(myDataList);
            } else {
                return -1;
            }
            return 1;
        });
        return 1;
    }

    /**
     * Returns the LiveData object for the list of UserGet objects.
     *
     * @return The LiveData object containing the list of UserGet objects.
     */
    public LiveData<List<UserGet>> getListUsersGet() {
        return listUserGets;
    }

    /**
     * Reloads the UserGet objects from the database.
     */
    public void reloadUserGetFromDb() {
        listUserGets.postValue(UserGet.getAllUsers());
    }

    /**
     * Clears the data associated with the logged out user.
     */
    public void clearLogoutUser() {
        messageDao.deleteAllMessages();
        UserGet.deleteAllUsers();
        userMessageConnectDao.deleteAllMessages();
        lastMsgByuser.deleteAllUsers();
        listMessages.setValue(new ArrayList<>());
        listusers.setValue(new ArrayList<>());
        listUserGets.setValue(new ArrayList<>());
    }

    /**
     * Returns the username of the last logged-in user.
     *
     * @return The username of the last logged-in user.
     */
    public String getLastUserLogin() {
        return lastUserLogin.getlastUserLogin();
    }

    /**
     * Sets the username of the last logged-in user.
     *
     * @param username The username of the last logged-in user.
     */
    public void setLastUserLogin(String username) {
        lastUserLogin.deleteAllUsers();
        lastUserLogin.insertUser(username);
    }

    /**
     * Loads messages of a user from the database.
     *
     * @param id The ID of the user.
     */
    public void loadMsgOfUserFromDb(String id) {
        List<String> list = userMessageConnectDao.getMessageIdsForUser(id);
        List<Message> list1 = new ArrayList<>();
        for (String s : list) {
            List<Message> list2 = messageDao.getMessagesById(s);
            list1.add(list2.get(0));
        }
        Collections.reverse(list1);
        listMessages.postValue(list1);
    }

    /**
     * Loads messages of a user from the API and updates the database.
     *
     * @param id The ID of the user.
     */
    public void loadMsgOfUserFromApi(String id) {
        CompletableFuture<List<Message>> future = mainApiManger.getMessagesByUser(id);
        future.thenApply(messages -> {
            if (messages != null) {
                List<Message> list = messageDao.getAllMessages();
                for (Message message : messages) {
                    if (!list.contains(message)) {
                        messageDao.insertMessage(message);
                        UserMessage userMessage = new UserMessage(id, message.getId());
                        userMessageConnectDao.insert(userMessage);
                    }
                }
                loadMsgOfUserFromDb(id);
                return 1;
            } else {
                return -1;
            }
        });
    }

}