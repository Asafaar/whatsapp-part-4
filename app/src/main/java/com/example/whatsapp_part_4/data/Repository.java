package com.example.whatsapp_part_4.data;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_part_4.Activty.friends;
import com.example.whatsapp_part_4.Async.AsyncTaskUsers;
import com.example.whatsapp_part_4.Model.Model;

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
    private Model model;

    public Repository(Appdb db, Model model) {
        this.model = model;

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
        future.thenCompose(message -> {
            if (message != null) {
                Log.e("60", "sendMessage:work ");
                List<Message> myDataList = listmessages.getValue();
                myDataList.add(0, message);
                listmessages.setValue(myDataList);

                CompletableFuture<Integer> completableFuture = mainApiManger.sendMessageWithFirebase(message, friendusername);
                return completableFuture.thenApply(integer -> {
                    if (integer == 200) {
                        Log.e("70", "sendMessage: work ");
//                        userMessageConnectDao.insert(new UserMessage(idofFriend, message.getId()));
//                        lastMsgByuser.updateMessageById(idofFriend, message);
                        return 1;
                    } else {
                        Log.e("70", "sendMessage: dont work ");
                        return -1;
                    }
                });
            } else {
                Log.e("70", "sendMessage: dont work ");
                return CompletableFuture.completedFuture(-1);
            }
        }).exceptionally(throwable -> {
            Log.e("TAG", "sendMessage: Exception occurred: " + throwable.getMessage());
            return -1;
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
    CompletableFuture<DataUserRes> future = mainApiManger.getUserData(username);

    future.thenAccept(userData -> {
        if (userData != null) {
            Log.d("repository", "Received object: " + userData.toString());
        } else {
            Log.d("repository", "Received object is null");
        }
    });

    return future;
}

    public MutableLiveData<List<User>> getListusers() {
        return listusers;
    }

    public LiveData<List<User>> getfriendslist() {
        return listusers;
    }
    public void loadMsgOfUserfromDb(String id) {
        List<String> list=userMessageConnectDao.getMessageIdsForUser(id);
        List<Message> list1=new ArrayList<>();
        for (String s : list) {
         List<Message> list2=  messageDao.getMessagesById(s);
            list1.add(list2.get(0));
        }
        listmessages.postValue(list1);
    }
    public void loadMsgOfUserfromapi(String id) {
        CompletableFuture<List<Message>> future = mainApiManger.getMessgesByuser(id);
        future.thenApply(messages -> {
            if (messages != null) {
//                messageDao.deleteAllMessages();
//                messageDao.insertAll(messages);
//                userMessageConnectDao.deleteAllMessages();\
                List<Message> list=messageDao.getAllMessages();
                for (Message message : messages) {
                    if (!list.contains(message)){
                        messageDao.insertMessage(message);
                        UserMessage userMessage = new UserMessage(id, message.getId());
                        userMessageConnectDao.insert(userMessage);

                    }
                }
                loadMsgOfUserfromDb(id);

                return 1;
            } else {
                return -1;
            }
        });
    }
    public int getMessgesByuser(String id) {

        List<Message> list = messageDao.getMessagesById(id);
        listmessages.setValue(list);
        CompletableFuture<List<Message>> future = mainApiManger.getMessgesByuser(id);
        future.thenApply(messages -> {
            if (messages != null) {
//                messageDao.deleteAllMessages();
                messageDao.insertAll(messages);
//                userMessageConnectDao.deleteAllMessages();

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

//    public synchronized CompletableFuture<Integer> adduser(String Friend) {
//        CompletableFuture<Integer> future = new CompletableFuture<>();
//        mainApiManger.Addfriend(Friend)
//                .thenAccept(statusCode -> {
//                    if (statusCode == 200) {
//                        Log.e("TAG", "addnewfriend:load ");
//                        mainApiManger.getfriends();
//                        future.complete(1); // Complete with status 1 (success)
//                    } else if (statusCode == 401) {
//                        Log.e("TAG", "addnewfriend: -1");
//                        future.complete(-2); // Complete with status -2 (Unauthorized)
//                    } else if (statusCode == 400) {
//                        Log.e("TAG", "addnewfriend: -1");
//                        future.complete(-3); // Complete with status -3 (Bad Request)
//                    } else {
//                        future.complete(-1); // Complete with status -1 (Unknown error)
//                    }
//                })
//                .exceptionally(throwable -> {
//                    future.complete(-1); // Complete with status -1 (Unknown error)
//                    return null;
//                });
//        return future;
//    }


    public synchronized CompletableFuture<Integer> addNewFriend(String Friend) {
        return mainApiManger.Addfriend(Friend)
                .thenCompose(statusCode -> {
                    if (statusCode == 200) {
                        Log.e("TAG", "addnewfriend:load ");
                        return mainApiManger.getfriends()
                                .thenApply(response -> {
                                    // Process the response here
                                    return 1; // Success
                                });
                    } else if (statusCode == 401) {
                        return CompletableFuture.completedFuture(-2); // Unauthorized
                    } else if (statusCode == 400) {
                        return CompletableFuture.completedFuture(-3); // Bad Request
                    } else {
                        Log.e("TAG", "addnewfriend: -1");
                        return CompletableFuture.completedFuture(-1); // Unknown error
                    }
                })
                .exceptionally(throwable -> {
                    Log.e("TAG", "addnewfriend: -1");
                    return -1; // Unknown error
                });
    }
    public void addmessage(Message message) {
        List<Message> myDataList = listmessages.getValue();//add to live data
        myDataList.add(0, message);
        listmessages.postValue(myDataList);

//        listmessages.notify();
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
        AsyncTaskUsers messageLoader = new AsyncTaskUsers(model);
        messageLoader.execute();

//        List<UserGet> userGetList = useserGet.getAllUsers();
//        listUserGets.setValue(userGetList);
//        CompletableFuture<Integer> future= mainApiManger.getfriends();
//        future.thenApply(statusCode -> {
//            if (statusCode == 200) {
//                Log.e("TAG", "reloadusers: " + "load" );
//                List<UserGet> userGetList1 = useserGet.getAllUsers();
//                listUserGets.setValue(userGetList1);
//                return 1;
//            } else {
//                return -1;
//            }
//        });
    }

    public void reloadusersOntheback() {


        CompletableFuture<Integer> future= mainApiManger.getfriends();
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                Log.e("TAG", "reloadusers: " + "load" );
                List<UserGet> userGetList1 = useserGet.getAllUsers();
                listUserGets.postValue(userGetList1);

                return 1;
            } else {
                return -1;
            }
        });
    }

    public CompletableFuture<Integer> trylogin(String username, String password) {
        return mainApiManger.trylogin(username, password);


    }

    //when make register//TODO need to add the error by status code
    public CompletableFuture<Integer>  MakeNewUser(String username, String password, String displayName, String profilePic) {

        return mainApiManger.makenewuser(username, password, displayName, profilePic);
    }

    public void sendTokenfirebasedel(String username) {

        CompletableFuture<Integer> future= mainApiManger.sendTokenfirebasedel(username);
        future.thenApply(statusCode -> {
            if (statusCode == 200) {
                Log.e("TAG", "sendTokenfirebasedel: " + "load" );
                return 1;
            } else {
                return -1;
            }
        });
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

    public void reloadusergetfromdb() {
        listUserGets.postValue(useserGet.getAllUsers());

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
