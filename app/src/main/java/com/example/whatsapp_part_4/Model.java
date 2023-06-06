package com.example.whatsapp_part_4;

import androidx.lifecycle.LiveData;

import com.example.whatsapp_part_4.data.Appdb;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.Repository;
import com.example.whatsapp_part_4.data.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {
    private LiveData<List<Message>> messages;
    private LiveData<List<User>> users;
    private Repository repository;

    public Model(Appdb db) {
        this.repository = new Repository(db);
        messages = repository.getListmessages();
        users = repository.getListusers();
    }

    public void reload() {
        repository.reloadusers();
    }
    public synchronized void sendmsg(String  idofFriend,String msg,String username ,String displayName,  byte[] profilePic){
        repository.sendmsg(idofFriend,msg,username,displayName,profilePic);
    }

    public synchronized CompletableFuture<Integer> trylogin(String username, String password) {
        return repository.trylogin(username, password);
    }

    public void makenewuser(String username, String password, String displayName, byte[] profilePic) {
        repository.MakeNewUser(username, password, displayName, profilePic);
    }
    public synchronized void getMessgesByuser(String id){
        repository.getMessgesByuser(id);
    }
    public synchronized void deleteFriend(User user){ {
        repository.deleteFriend(user);
    }
}
public String  gettoken(){
    return    repository.getToken();
}
    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<List<User>> getUsers() {
        return users;
}
}
