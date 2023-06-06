package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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


    public Repository(Appdb db) {
        this.db = db;
//        userDao = db.userDao();
        messageDao = db.messageDao();
        userMessageConnectDao = db.userMessageConnectDao();
        lastMsgByuser = db.lastMsgByuser();
        useserGet = db.UeserGet();
        listusers = new ListUsers();
        listmessages = new ListMessage();
        listUserGets = new ListUserGet();
        mainApiManger = new MainApiManger(listusers,listUserGets, listmessages,  messageDao, null, userMessageConnectDao,useserGet);
    }

    public MutableLiveData<List<Message>> getListmessages() {
        return listmessages;
    }

    public void sendmsg(String idofFriend, String msg, String username, String displayName, byte[] profilePic) {
        Message message = mainApiManger.sendmsg(idofFriend, msg, username, displayName, profilePic);
        messageDao.insertMessage(message);
        List<Message> myDataList = listmessages.getValue();//add to live data
        myDataList.add(message);
        listmessages.postValue(myDataList);
        userMessageConnectDao.insert(new UserMessage(idofFriend, message.getId()));//add to user message connect
        lastMsgByuser.updateMessageById(idofFriend,message);//update last message


    }

    public String getToken() {
        return token;
    }

    public MutableLiveData<List<User>> getListusers() {
        return listusers;
    }

    public LiveData<List<User>> getfriendslist() {
        return listusers;
    }

    public void getMessgesByuser(String id) {
        mainApiManger.getMessgesByuser(id);
    }

    public void adduser(User user) {
//        userDao.insertUser(user);
        List<User> myDataList = listusers.getValue();//add to live data
        myDataList.add(user);
        listusers.postValue(myDataList);

    }

    public void addmessage(Message message) {

    }

    public void updateuser(User user) {

    }

    public void deletemessage(Message message) {

    }

    public void deleteuser(User user) {

    }

    public void reloadmessg(String id) {
        mainApiManger.getMessgesByuser(id);
    }

    public void reloadusers() {
        mainApiManger.getfriends();
    }

    public CompletableFuture<Integer> trylogin(String username, String password) {
        return mainApiManger.trylogin(username, password);


    }

    //when make register
    public void MakeNewUser(String username, String password, String displayName, byte[] profilePic) {
        mainApiManger.makenewuser(username, password, displayName, profilePic);

    }

    public int deleteFriend(User user) {
        int statusapi = mainApiManger.deleteFriend(user.getId());
        if (statusapi != 200) {//cant del from server
            return -1;
        }
//        userDao.deleteUser(user);
        List<String> list = userMessageConnectDao.getMessageIdsForUser(user.getId());
        for (String id : list) {
            messageDao.deleteMessageById(id);
        }
        userMessageConnectDao.deleteMessagesForUser(user.getId());

        List<User> myDataList = listusers.getValue();
        myDataList.remove(user);
        listusers.postValue(myDataList);
        lastMsgByuser.deleteMessageById(user.getId());
        return 1;
    }

    public LiveData<List<UserGet>> getListusersget() {
        return listUserGets;
    }
}
