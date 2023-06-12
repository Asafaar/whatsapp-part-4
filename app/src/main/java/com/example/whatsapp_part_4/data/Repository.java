package com.example.whatsapp_part_4.data;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_part_4.Activty.friends;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Repository {
    //    private UserDao userDao;
    private MessageDao messageDao;
    private String token;
    private ListUsers listusers;

    private ListUserGet listUserGets;
    private Appdb db;
    private ListMessage listmessages;
    private MainApiManger mainApiManger;
    private UserMessageConnectDao userMessageConnectDao;

    private LastMsgByuser lastMsgByuser;

    private UeserGet useserGet;
    private ThemeSave themeSavedb;
    private String username;
    private String displayName;
    private String profilePic;

    public Repository(Appdb db) {
        this.db = db;
//        userDao = db.userDao();
        messageDao = db.messageDao();
        userMessageConnectDao = db.userMessageConnectDao();
        lastMsgByuser = db.lastMsgByuser();
        useserGet = db.UeserGet();
        themeSavedb = db.ThemeSave();
        listusers = new ListUsers();
        listmessages = new ListMessage();
        listUserGets = new ListUserGet();
        mainApiManger = new MainApiManger(listusers, listUserGets, listmessages, messageDao, null, userMessageConnectDao, useserGet);
    }

    public MutableLiveData<List<Message>> getListmessages() {
        return listmessages;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int sendMessage(String idofFriend, String msg, String username, String displayName, byte[] profilePic, String friendusername) {
//        Message message = mainApiManger.sendMessage(idofFriend, msg, username, displayName, profilePic);
//        messageDao.insertMessage(message);
//        List<Message> myDataList = listmessages.getValue();//add to live data
//        myDataList.add(message);
//        listmessages.setValue(myDataList);
//        userMessageConnectDao.insert(new UserMessage(idofFriend, message.getId()));//add to user message connect
//        lastMsgByuser.updateMessageById(idofFriend, message);//update last message
        CompletableFuture<Message> future = mainApiManger.sendMessage(idofFriend, msg, username, displayName, profilePic);
        future.thenApply(message -> {
            if (message != null) {
                Log.e("60", "sendMessage:work ");
                mainApiManger.getfriends();//get all the data of the users from the web because we need his id
                messageDao.insertMessage(message);
                List<Message> myDataList = listmessages.getValue();//add to live data
                myDataList.add(message);
                listmessages.setValue(myDataList);
                userMessageConnectDao.insert(new UserMessage(idofFriend, message.getId()));//add to user message connect
                lastMsgByuser.updateMessageById(idofFriend, message);//update last message
                mainApiManger.sendMessageWithFirebase(message, friendusername);//send message to firebase
                return 1;
            } else {
                Log.e("70", "sendMessage: dont work ");

                return -1;
            }

        });
        Log.e("60", "sendMessage:end send -1 ");

        return 1;
    }
    public void setRetrofit(String url){
        mainApiManger.setRetrofit(url);
    }
    public String getToken() {
        return token;
    }

    public void setTheme(int Theme) {
        this.themeSavedb.deleteTheme();
        this.themeSavedb.insertTheme(String.valueOf(Theme));
    }

    public ThemeString getTheme() {
//        Log.e("TAG", "getTheme: "+this.themeSavedb.getTheme().theme );
        if (this.themeSavedb.getTheme() == null) {
            Log.e("TAG", "getTheme: " );
            return null;
        } else{

            return this.themeSavedb.getTheme();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

//    public void getUserData(String username) {
//        CompletableFuture<DataUserRes> completableFuture=  mainApiManger.getUserData(username);
//        completableFuture.thenApply(dataUserRes -> {
//            if (dataUserRes != null) {
//                this.username = dataUserRes.getUsername();
//                this.displayName = dataUserRes.getDisplayName();
//                this.profilePic = dataUserRes.getProfilePic();
//                return 1;
//            } else {
//                return -1;
//            }
//        });
//
//    }
public CompletableFuture<DataUserRes> getUserData(String username) {
    return mainApiManger.getUserData(username);
}
    public void sendMessageWithFirebase(Message message, String friendusername) {
        mainApiManger.sendMessageWithFirebase(message, friendusername);
    }

    public MutableLiveData<List<User>> getListusers() {
        return listusers;
    }

    public LiveData<List<User>> getfriendslist() {
        return listusers;
    }

    public int getMessgesByuser(String id) {

        List<Message> list = messageDao.getMessagesById(id);
        listmessages.setValue(list);
        CompletableFuture<List<Message>> future = mainApiManger.getMessgesByuser(id);
        future.thenApply(messages -> {
            if (messages != null) {
                messageDao.deleteAllMessages();
                messageDao.insertAll(messages);
                userMessageConnectDao.deleteAllMessages();

                for (Message message : messages) {//add to the db connect table
                    UserMessage userMessage = new UserMessage(id, message.getId());
                    userMessageConnectDao.insert(userMessage);

                    listmessages.setValue(messages);
                }
                return 1;
            } else {
                return -1;
            }
        });
        return 1;
    }

    public int adduser(String frienduser) {
//        userDao.insertUser(user);

        CompletableFuture<Integer> future = mainApiManger.Addfriend(frienduser);
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                Log.e("TAG", "adduser:load ");
                mainApiManger.getfriends();//get all the data of the users from the web because we need his id
            } else {
                Log.e("TAG", "adduser: -1");

                return -1;
            }
            Log.e("TAG", "adduser: 1");
            return 1;
        });
        Log.e("TAG", "adduser: 1");

        return 1;

    }

    public void addmessage(Message message) {
        listmessages.getValue().add(message);
    }

    public void updateuser(User user) {

    }

    public void deletemessage(Message message) {

    }

    public void deleteuser(User user) {

    }

    public CompletableFuture<Integer> registerfirebase(String token, String username) {
        return mainApiManger.registerfirebase(token, username);
    }

    public void reloadmessg(String id) {
        mainApiManger.getMessgesByuser(id);
    }

    public void reloadusers() {

        List<UserGet> userGetList = useserGet.getAllUsers();
        listUserGets.setValue(userGetList);
        mainApiManger.getfriends();
    }

    public CompletableFuture<Integer> trylogin(String username, String password) {
        return mainApiManger.trylogin(username, password);


    }

    //when make register//TODO need to add the error by status code
    public CompletableFuture<Integer>  MakeNewUser(String username, String password, String displayName, String profilePic) {

        return mainApiManger.makenewuser(username, password, displayName, profilePic);
    }

    public void sendTokenfirebasedel(String token) {
        mainApiManger.sendTokenfirebasedel(token);
    }

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
                myDataList.remove(user);
                listUserGets.setValue(myDataList);
            } else {
                return -1;
            }
            return 1;
        });
//        userDao.deleteUser(user);
//        List<String> list = userMessageConnectDao.getMessageIdsForUser(user.getId());
//        for (String id : list) {
//            messageDao.deleteMessageById(id);
//        }
//        userMessageConnectDao.deleteMessagesForUser(user.getId());
//
////        List<User> myDataList = listusers.getValue();
////        myDataList.remove(user);
////        listusers.postValue(myDataList);
//        List<UserGet> myDataList = listUserGets.getValue();
//        myDataList.remove(user);
//        listUserGets.setValue(myDataList);
//        lastMsgByuser.deleteMessageById(user.getId());
        return 1;
    }

    public LiveData<List<UserGet>> getListusersget() {
        return listUserGets;
    }

    public void reloadlastmsg() {
        List<UserGet> userGetList = useserGet.getAllUsers();

    }

    public void clearLogoutUser() {

        messageDao.deleteAllMessages();
        useserGet.deleteAllUsers();
        userMessageConnectDao.deleteAllMessages();
        lastMsgByuser.deleteAllUsers();
        listmessages.setValue(new ArrayList<>());
        listusers.setValue(new ArrayList<>());
        listUserGets.setValue(new ArrayList<>());
    }
}
