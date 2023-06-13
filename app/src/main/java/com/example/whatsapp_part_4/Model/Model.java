package com.example.whatsapp_part_4.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.DataUserRes;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.Repository;
import com.example.whatsapp_part_4.data.ThemeString;
import com.example.whatsapp_part_4.data.User;
import com.example.whatsapp_part_4.data.UserGet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {
    private LiveData<List<Message>> messages;//the local data message that will be on the screnn
    private LiveData<List<User>> users;//not need TODO delete
    private LiveData<List<UserGet>> usersget;//the local data friend that will be on the screnn

    private Repository repository;

    public Model(Appdb db) {
        this.repository = new Repository(db);
        messages = repository.getListmessages();
        users = repository.getListusers();
        usersget = repository.getListusersget();
    }

    public String getUserDisplayname(){
        return  repository.getDisplayName();
    }
    public String getUserimg(){
        return  repository.getProfilePic();
    }

    public void setdisplayname(String displayname){
        repository.setDisplayName(displayname);
    }
    public void setprofilepic(String profilepic){
        repository.setProfilePic(profilepic);
    }
    /**reload
     * @return reload the users from the db and from the server to the livedata
     */
    public void reload() {
        repository.reloadusers();
    }

    /** sendMessage to the server
     *
     * @param idofFriend the id of the friend
     * @param msg the message
     * @param username the username of the user
     * @param displayName the display name of the user
     * @param profilePic the profile pic of the user
     * @param friendusername the username of the friend
     */
    public synchronized void sendMessage(String idofFriend, String msg, String username, String displayName, byte[] profilePic,String friendusername) {
        int status= repository.sendMessage(idofFriend, msg, username, displayName, profilePic,friendusername);
        if (status==1){
            Log.e("TAG", "sendMessage: sucss" );
        }else{
            Log.e("TAG", "sendMessage: fail" );

        }
    }

    public ThemeString getTheme(){
        if (repository.getTheme()==null){
            Log.e("TAG", "getTheme: " + "null" );
           return null;
        }else{

            return  repository.getTheme();
        }

    }
    public CompletableFuture<DataUserRes> getUserData(String username){
        CompletableFuture<DataUserRes> future = repository.getUserData(username);

        future.thenAccept(userData -> {
            if (userData != null) {
                Log.d("model", "Received object: " + userData.toString());
            } else {
                Log.d("model", "Received object is null");
            }
        });

        return future;
    }

    public void setRetrofit(String url){
        repository.setRetrofit(url);
    }

    public void setTheme(int Theme){
         repository.setTheme(Theme);
    }

    /**
     * trylogin to the server
     * @param username the username
     * @param password the password
     * @return if the login is sucsses
     */
    public synchronized CompletableFuture<Integer> trylogin(String username, String password) {
        return repository.trylogin(username, password);
    }

    /**
     * makenewuser- make new user in the server
     * @param username the username
     * @param password the password
     * @param displayName the display name
     * @param profilePic the profile pic
     */
    public CompletableFuture<Integer> makenewuser(String username, String password, String displayName, String profilePic) {
       return repository.MakeNewUser(username, password, displayName, profilePic);
    }

    /**
     * getMessgesByuser
     * @param id the id of the user we want to get the messages
     */
    public synchronized int getMessgesByuser(String id) {
        int status=repository.getMessgesByuser(id);
        if (status==1){
            Log.e("TAG", "getMessgesByuser: sucss" );
            return 1;
        }else{
            Log.e("TAG", "getMessgesByuser: fail" );
            return -1;

        }
    }

//    /**
//     * sendTokenfirebasedel- register the token to the server
//     * @param usernmae the username
//     */
//    public synchronized void sendTokenfirebasedel(String usernmae) {
//        repository.sendTokenfirebasedel(usernmae);
//    }

    /**
     * deleteFriend-frm the server ,db and livedata
     * @param user the user we want to delete
     */
    public synchronized void deleteFriend(UserGet user) {
            repository.deleteFriend(user);
    }

    /**
     * addnewfriend- add new friend to the server search if the friend is in the server
     * @param Friend the friend we want to add
     * @return if the friend is in the server and can be added
     */
    public synchronized int addnewfriend(String Friend){
       return repository.adduser(Friend);


    }

    /**
     * registerfirebase- register the token to the server
     * @param token the token
     * @param username the username
     * @return if the register is sucsses
     */
    public CompletableFuture<Integer> registerfirebase(String token,String username){
      return   repository.registerfirebase(token,username);
    }

    /**
     * addmessage- add message to livedata when the user send message to the friend so need to add the message to the livedata
     * @param message the message
     */
    public void addmessage(Message message) {
        repository.addmessage(message);
    }

    /**
     * gettoken- get the token from the repository
     * @return the token
     */
    public String gettoken() {
        return repository.getToken();
    }

    /**
     * getMessages
     * @return the messages
     */
    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    /**
     * getUsers
     * @return the users
     */
    public LiveData<List<User>> getUsers() {
        return users;
    }

    /**
     * getUsersget
     * @return the usersget
     */
    public LiveData<List<UserGet>> getUsersget() {
        return usersget;
    }

    /**
     * getlastmsg
     */
    public void reloadlastmsg(){
        repository.reloadlastmsg();
    }

    /**
     * clearLogoutUser- clear the logout user from the repository
     */
    public void clearLogoutUser() {
        repository.clearLogoutUser();
    }
}
