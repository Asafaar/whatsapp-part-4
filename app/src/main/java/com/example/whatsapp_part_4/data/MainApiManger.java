package com.example.whatsapp_part_4.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApiManger {
    private MutableLiveData<List<User>> users;
    private MutableLiveData<List<Message>> messages;
    private Retrofit retrofit;
    private WebserviceApi api;
    private UserDao userDao;
    private MessageDao messageDao;
    private UserMessageConnectDao userMessageConnectDao;
    private String token;

    public MainApiManger(MutableLiveData<List<User>> users, MutableLiveData<List<Message>> messages, UserDao userDao, MessageDao messageDao, String token,UserMessageConnectDao userMessageConnectDao) {
        this.users = users;
        this.token = "bearer" + token;
        this.messages = messages;
        this.userMessageConnectDao = userMessageConnectDao;
        this.userDao = userDao;
        this.messageDao = messageDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantData.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        api = retrofit.create(WebserviceApi.class);

    }

    public void getfriends() {

        api.getFriends(token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userDao.deleteAllUsers();
                userDao.insertAll(response.body());
                users.setValue(userDao.getAllUsers());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                users.setValue(userDao.getAllUsers());
            }
        });

    }
    //   working
    public Message sendmsg(String  idofFriend,String msg,String username, String displayName,  byte[] profilePic){
        SendMsgRequest sendMsgRequest = new SendMsgRequest(msg,username,displayName,profilePic);
        final Message[] message = {null};
//        final CountDownLatch latch = new CountDownLatch(1);

        api.sendMessage(idofFriend,sendMsgRequest,token).enqueue(new Callback<Message>() {
        @Override
        public void onResponse(Call<Message> call, Response<Message> response) {
            if(response.code()==200){
                     message[0] = response.body();
                Log.d("sendmsg","send successfully");
            }
            else {
                Log.d("sendmsg","Error: "+response.code());
            }


        }

        @Override
        public void onFailure(Call<Message> call, Throwable t) {


        }
    });

    return message[0];
    }
//work but dont have the displayname and prgofile pic on the sender
    public void getMessgesByuser(String id) {
        api.getMessage(id,token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messageDao.deleteAllMessages();
                List<Message> messageList=response.body();
                messageDao.insertAll(messageList);
                if (messageList!=null){
                for (Message message:messageList) {//add to the db connect table
                    UserMessage userMessage = new UserMessage(id, message.getId());
                    userMessageConnectDao.insert(userMessage);
                }
                    List<Message> messageslist=messages.getValue();//add to main msg list
                    messageslist.addAll(messageList);
                    messages.setValue(messageslist);}

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public CompletableFuture<Integer> trylogin(String username, String password) {

        LoginRequest loginRequest = new LoginRequest(username, password);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        final int[] statusCode = new int[1];
        api.getToken(loginRequest).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    Log.d("login", "login successfully");
                    token = response.body();
                    token = "bearer"+" " + token;
                    Log.d("token", token);
                } else {
                    Log.d("login", "Error: " + statusCode[0]);
                }
                future.complete(statusCode[0]);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                statusCode[0]=-1;
                Log.d("token", "fail");
                future.complete(-1);            }
        });
//        try {
//
//            latch.await();
//        }
//        catch (Exception e){
//            Log.d("token", "fail");
//        }
        return future;
    }
    int makenewuser(String username,String password, String displayName,  byte[] profilePic){

        UserRequest user = new UserRequest(username, password, displayName,  Base64.getEncoder().encodeToString(profilePic));
        final CountDownLatch latch = new CountDownLatch(1);
        final int[] statusCode = new int[1];
        api.createUser(user).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    Log.d("createUser", "User created successfully");
                } else {
                    Log.d("createUser", "Error: " + statusCode[0]);
                }
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Void>call, Throwable t) {
                statusCode[0]=-1;
                Log.d("token", "fail");
                latch.countDown();
            }
        });
        try {

            latch.await();
        }
        catch (Exception e){
            Log.d("token", "fail");
        }
        return statusCode[0];
    }

    public int deleteFriend(String friendid) {
        final int[] statusCode = new int[1];

        api.deleteFriend(friendid,token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    statusCode[0]=200;
                    Log.d("deleteFriend","deleted successfully");
                }
                else {
                    Log.d("deleteFriend","Error: "+response.code());
                    statusCode[0]=response.code();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                statusCode[0]=-1;
            }
        });
    return statusCode[0];
    }
}

