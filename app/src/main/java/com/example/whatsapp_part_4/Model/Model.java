package com.example.whatsapp_part_4.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.Repository;
import com.example.whatsapp_part_4.data.User;
import com.example.whatsapp_part_4.data.UserGet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {
    private LiveData<List<Message>> messages;
    private LiveData<List<User>> users;
    private LiveData<List<UserGet>> usersget;

    private Repository repository;

    public Model(Appdb db) {
        this.repository = new Repository(db);
        messages = repository.getListmessages();
        users = repository.getListusers();
        usersget = repository.getListusersget();
    }

    public void reload() {
        repository.reloadusers();
    }

    public synchronized void sendMessage(String idofFriend, String msg, String username, String displayName, byte[] profilePic,String friendusername) {
        int status= repository.sendMessage(idofFriend, msg, username, displayName, profilePic,friendusername);
        if (status==1){
            Log.e("TAG", "sendMessage: sucss" );
        }else{
            Log.e("TAG", "sendMessage: fail" );

        }
    }

    public synchronized void sendMessageWithFirebase(Message message,String usernmaefirned) {
        repository.sendMessageWithFirebase(message,usernmaefirned);
    }



    public synchronized CompletableFuture<Integer> trylogin(String username, String password) {
        return repository.trylogin(username, password);
    }

    public void makenewuser(String username, String password, String displayName, byte[] profilePic) {
        repository.MakeNewUser(username, password, displayName, profilePic);
    }

    public synchronized void getMessgesByuser(String id) {
        int status=repository.getMessgesByuser(id);
        if (status==1){
            Log.e("TAG", "getMessgesByuser: sucss" );
        }else{
            Log.e("TAG", "getMessgesByuser: fail" );

        }
    }
    public synchronized void sendTokenfirebasedel(String usernmae) {
        repository.sendTokenfirebasedel(usernmae);
    }

    public synchronized void deleteFriend(UserGet user) {

            repository.deleteFriend(user);

    }
    public synchronized int addnewfriend(String Friend){
       return repository.adduser(Friend);


    }
    public CompletableFuture<Integer> registerfirebase(String token,String username){
      return   repository.registerfirebase(token,username);
    }
    public void addmessage(Message message) {
        repository.addmessage(message);
    }
    public String gettoken() {
        return repository.getToken();
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<List<UserGet>> getUsersget() {
        return usersget;
    }

    public void reloadlastmsg(){
        repository.reloadlastmsg();
    }

    public void clearLogoutUser() {
        repository.clearLogoutUser();
    }
}
