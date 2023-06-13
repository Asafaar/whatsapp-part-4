package com.example.whatsapp_part_4.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApiManger {
    private final MutableLiveData<List<User>> users;
    private final MutableLiveData<List<Message>> messages;
    private final MutableLiveData<List<UserGet>> usersget;
    private Retrofit retrofit;
    private final WebserviceApi api;
    //    private UserDao userDao;
    private final MessageDao messageDao;
    private final UserMessageConnectDao userMessageConnectDao;
    private String token;
    private final UeserGet UeserGet;

    public MainApiManger(MutableLiveData<List<User>> users, MutableLiveData<List<UserGet>> userget, MutableLiveData<List<Message>> messages, MessageDao messageDao, String token, UserMessageConnectDao userMessageConnectDao, UeserGet ueserGet) {
        this.users = users;
        this.token = "bearer" + token;
        this.messages = messages;
        this.usersget = userget;
        this.UeserGet = ueserGet;
        this.userMessageConnectDao = userMessageConnectDao;
//        this.userDao = userDao;
        this.messageDao = messageDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantData.BASE_URL5)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        api = retrofit.create(WebserviceApi.class);

    }

    public void setRetrofit(String url) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
    }

    public CompletableFuture<Integer> getfriends() {//TODO add check
        CompletableFuture<Integer> integerCompletableFuture = new CompletableFuture<>();
        Log.e("TAG", "getfriends: "+token );
        api.getFriends(token).enqueue(new Callback<List<UserGet>>() {
            @Override
            public void onResponse(Call<List<UserGet>> call, Response<List<UserGet>> response) {
//                userDao.deleteAllUsers();
//                userDao.insertAll(response.body());
                if (response.code() == 200) {
                    UeserGet.deleteAllUsers();
                    if (response.body() != null) {
                        UeserGet.insertAll(response.body());
//                        usersget.setValue(response.body());
                    }
                    integerCompletableFuture.complete(200);
                }
                else {
                    Log.e("TAG", "onResponse: getfriends"+response.code() );
                    integerCompletableFuture.complete(response.code());
                }
//update db
//                List<UserGet> myDataList = usersget.getValue();//update livedata
//                myDataList.addAll(response.body());


            }

            @Override
            public void onFailure(Call<List<UserGet>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
                                integerCompletableFuture.complete(-1);
            }
        });
        return integerCompletableFuture;
    }

    //   working
    public CompletableFuture<Message> sendMessage(String idofFriend, String msg, String username, String displayName, byte[] profilePic) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(msg);
         Message[] message = {null};
//        final CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture<Message> future = new CompletableFuture<>();
        api.sendMessage(idofFriend, sendMessageRequest, token).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.e("TAG", "onResponse: "+response.code() );
                if (response.code() == 200) {
                    Log.e("TAG", "onResponse: " + 200);
                    message[0] = response.body();
                    Log.e("TAG", "onResponse: "+message[0].getCreated() );
                    future.complete(message[0]);
//                 UserMessage userMessage = new UserMessage(idofFriend, message[0].getId());//TODO check if it works
//                userMessageConnectDao.update(userMessage);
//                List<UserGet> list=usersget.getValue();
//                for (UserGet userGet:list) {
//                    if(userGet.getId().equals(idofFriend)){
//                        userGet.getLastMessage().setContent(message[0].getContent());
//                        userGet.getLastMessage().setCreated(message[0].getCreated());
//                        break;
//                    }
//                }
//                usersget.setValue(list);
//                messageDao.insertMessage(message[0]);
//                List<Message> list1=messages.getValue();
//                list1.add(message[0]);
//                messages.setValue(list1);

                    Log.d("sendmsg", "send successfully");
                } else {
                    Log.d("sendmsg", "Error: " + response.code());
                }


            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {


            }
        });

        return future;
    }

    //Todo dont get all date
    public CompletableFuture<List<Message>> getMessgesByuser(String id) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        api.getMessage(id, token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messageList = response.body();
                future.complete(messageList);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
        return future;
    }

    public CompletableFuture<Integer> registerfirebase(String token, String username) {
        reqfirebase reqfirebase = new reqfirebase(token, username);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendTokenfirebase(reqfirebase).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d("firebase registerfirebase", "send successfully");
                    future.complete(200);
                } else {
                    Log.d("firebase registerfirebase", "Error: " + response.code());
                    future.complete(404);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> sendTokenfirebasedel(String username) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "sendTokenfirebasedel: "+username );
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendTokenfirebasedel(json).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("firebase", "send successfully");
                future.complete(200);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("firebase", "Error: " + t.getMessage());
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> Addfriend(String userfrined) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        Log.e("152", "Addfriend: " + userfrined);
        Log.e("152", "Addfriend: " + token);
        FindFriendRequest findFriendRequest = new FindFriendRequest(userfrined);
        api.addUserFrined(findFriendRequest, token).enqueue(new Callback<UserGet.User>() {
            @Override
            public void onResponse(Call<UserGet.User> call, Response<UserGet.User> response) {
                future.complete(response.code());
            }

            @Override
            public void onFailure(Call<UserGet.User> call, Throwable t) {
                Log.d("addfriend", "Error: onFailure");
                future.complete(404);
            }
        });

        return future;
    }

    public CompletableFuture<DataUserRes> getUserData(String username) {
        Log.e("TAG", "getUserData: " + username);
        CompletableFuture<DataUserRes> future = new CompletableFuture<>();
        api.getUserData(username, token).enqueue(new Callback<DataUserRes>() {
            @Override
            public void onResponse(Call<DataUserRes> call, Response<DataUserRes> response) {
                if (response.code() == 200) {
                    Log.d("getUserData", "get successfully");
                    DataUserRes userData = response.body();
                    if (userData != null) {
                        Log.d("getUserData", "Response body: " + userData.displayName);
                    } else {
                        Log.d("getUserData", "Response body is null");
                    }
                    future.complete(userData);
                } else {
                    Log.d("getUserData", "Error: " + response.code());
                    future.complete(null);
                }
            }

            @Override
            public void onFailure(Call<DataUserRes> call, Throwable t) {
                Log.d("getUserData", "Error: onFailure");
                future.complete(null);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> trylogin(String username, String password) {

        LoginRequest loginRequest = new LoginRequest(username, password);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CompletableFuture<Integer> future = new CompletableFuture<>();
        final int[] statusCode = new int[1];
        Log.e("TAG", "trylogin: " + loginRequest.getUsername() + " " + loginRequest.getPassword());
        api.getToken(loginRequest).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    Log.d("login", "login successfully");
                    token = response.body();
                    token = "bearer" + " " + token;
                    Log.d("token", token);
                    future.complete(statusCode[0]);
                } else {
                    Log.d("login", "Error: " + statusCode[0]);
                }
                future.complete(statusCode[0]);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                statusCode[0] = -1;
                Log.d("token", "fail");
                Log.e("TAG", "onFailure: " + t.getMessage());
                future.complete(-1);
            }
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


    public CompletableFuture<Integer> makenewuser(String username, String password, String displayName, String profilePic) {

        UserRequest user = new UserRequest(username, password, displayName, profilePic);
        final int[] statusCode = new int[1];
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.createUser(user).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    future.complete(200);
                    Log.d("createUser", "User created successfully");
                } else {
                    Log.d("createUser", "Error: " + statusCode[0]);
                    future.complete(statusCode[0]);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                statusCode[0] = -1;
                Log.d("token", "fail");
                future.complete(-1);
            }
        });
        try {

        } catch (Exception e) {
            Log.d("token", "fail");
        }
        return future;
    }

    public CompletableFuture<Integer> deleteFriend(String friendid) {
        final int[] statusCode = new int[1];
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.deleteFriend(friendid, token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204) {
                    statusCode[0] = 200;
                    Log.d("deleteFriend", "deleted successfully");
                    future.complete(200);
                } else {
                    Log.d("deleteFriend", "Error: " + response.code());
                    statusCode[0] = response.code();
                    future.complete(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                statusCode[0] = -1;
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> sendMessageWithFirebase(Message message, String friendusername) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        Log.e("TAG", "sendMessageWithFirebase: " +friendusername+message.getContent() );
        api.sendMessageWithFirebase(friendusername, message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d("firebase", "send successfully");
                    future.complete(200);
                } else {
                    Log.d("firebase", "Error: " + response.code());
                    future.complete(404);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("onFailure", "onFailure");
                future.complete(404);

            }
        });
        return future;
    }
}

