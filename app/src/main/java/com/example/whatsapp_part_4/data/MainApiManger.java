package com.example.whatsapp_part_4.data;

import android.util.Log;

import androidx.annotation.NonNull;

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
    private final WebserviceApi api;
    private final UeserGet UeserGet;
    private Retrofit retrofit;
    private String token;

    public MainApiManger(String token, UeserGet ueserGet) {
        this.token = "bearer" + token;
        this.UeserGet = ueserGet;
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantData.BASE_URL)
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

    public CompletableFuture<Integer> getFriends() {//TODO add check
        CompletableFuture<Integer> integerCompletableFuture = new CompletableFuture<>();
        api.getFriends(token).enqueue(new Callback<List<UserGet>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserGet>> call, @NonNull Response<List<UserGet>> response) {

                if (response.code() == 200) {
                    UeserGet.deleteAllUsers();
                    if (response.body() != null) {
                        UeserGet.insertAll(response.body());
                    }
                    integerCompletableFuture.complete(200);
                } else {
                    integerCompletableFuture.complete(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserGet>> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
                integerCompletableFuture.complete(-1);
            }
        });
        return integerCompletableFuture;
    }

    public CompletableFuture<Message> sendMessage(String idOfFriend, String msg) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(msg);
        Message[] message = {null};
        CompletableFuture<Message> future = new CompletableFuture<>();
        api.sendMessage(idOfFriend, sendMessageRequest, token).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.code() == 200) {
                    message[0] = response.body();
                    future.complete(message[0]);
                } else {
                    Log.d("sendMsg", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
            }
        });
        return future;
    }

    //Todo don't get all date
    public CompletableFuture<List<Message>> getMessagesByUser(String id) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        api.getMessage(id, token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                List<Message> messageList = response.body();
                future.complete(messageList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {

            }
        });
        return future;
    }

    public CompletableFuture<Integer> registerFireBase(String token, String username) {
        reqfirebase reqfirebase = new reqfirebase(token, username);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendTokenfirebase(reqfirebase).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    future.complete(200);
                } else {
                    future.complete(404);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> sendTokenFireBaseDel(String username) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendTokenfirebasedel(json).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                future.complete(200);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> AddFriend(String userFriend) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        FindFriendRequest findFriendRequest = new FindFriendRequest(userFriend);
        api.addUserFrined(findFriendRequest, token).enqueue(new Callback<UserGet.User>() {
            @Override
            public void onResponse(@NonNull Call<UserGet.User> call, @NonNull Response<UserGet.User> response) {
                future.complete(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<UserGet.User> call, @NonNull Throwable t) {
                future.complete(404);
            }
        });

        return future;
    }

    public CompletableFuture<DataUserRes> getUserData(String username) {
        CompletableFuture<DataUserRes> future = new CompletableFuture<>();
        api.getUserData(username, token).enqueue(new Callback<DataUserRes>() {
            @Override
            public void onResponse(@NonNull Call<DataUserRes> call, @NonNull Response<DataUserRes> response) {
                if (response.code() == 200) {
                    DataUserRes userData = response.body();
                    future.complete(userData);
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataUserRes> call, @NonNull Throwable t) {
                future.complete(null);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> tryLogin(String username, String password) {

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
        api.getToken(loginRequest).enqueue(new Callback<String>() {

            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    token = response.body();
                    token = "bearer" + " " + token;
                    future.complete(statusCode[0]);
                }
                future.complete(statusCode[0]);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                statusCode[0] = -1;
                future.complete(-1);
            }
        });
        return future;
    }


    public CompletableFuture<Integer> MakeNewUser(String username, String password, String displayName, String profilePic) {

        UserRequest user = new UserRequest(username, password, displayName, profilePic);
        final int[] statusCode = new int[1];
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.createUser(user).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                statusCode[0] = response.code();
                if (statusCode[0] == 200) {
                    future.complete(200);
                } else {
                    future.complete(statusCode[0]);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                statusCode[0] = -1;
                future.complete(-1);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> deleteFriend(String friendId) {
        final int[] statusCode = new int[1];
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.deleteFriend(friendId, token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 204) {
                    statusCode[0] = 200;
                    future.complete(200);
                } else {
                    statusCode[0] = response.code();
                    future.complete(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                statusCode[0] = -1;
                future.complete(404);
            }
        });
        return future;
    }

    public CompletableFuture<Integer> sendMessageWithFirebase(Message message, String friendUserName) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendMessageWithFirebase(friendUserName, message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    Log.d("firebase", "send successfully");
                    future.complete(200);
                } else {
                    Log.d("firebase", "Error: " + response.code());
                    future.complete(404);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                future.complete(404);

            }
        });
        return future;
    }
}