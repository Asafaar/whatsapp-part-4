package com.example.whatsapp_part_4.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

/**
 * MainApiManger is a class that manages API requests and communication with the server.
 * It uses Retrofit library for making network calls.
 */
public class MainApiManger {
    private WebserviceApi api;
    private final UeserGet UeserGet;
    private Retrofit retrofit;
    private String token;

    /**
     * Constructor for MainApiManger.
     *
     * @param token    The authentication token for API requests.
     * @param ueserGet An instance of the UeserGet class for performing database operations.
     */
    public MainApiManger(String token, UeserGet ueserGet) {
        this.token = "bearer" + token;
        this.UeserGet = ueserGet;
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantData.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        api = retrofit.create(WebserviceApi.class);
    }

    /**
     * Sets the base URL for Retrofit.
     *
     * @param url The base URL to set for Retrofit.
     */
    public void setRetrofit(Context context, String url) {
        try {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            Toast.makeText(context, "Set new connection", Toast.LENGTH_SHORT).show();
            api = retrofit.create(WebserviceApi.class);

        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves the friends list from the API.
     *
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
    public CompletableFuture<Integer> getFriends() {
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

    /**
     * Sends a message to a friend.
     *
     * @param idOfFriend The ID of the friend to send the message to.
     * @param msg        The message to send.
     * @return A CompletableFuture<Message> that completes with the sent message if successful.
     */
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
                    Log.e("sendMsg", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {}
        });
        return future;
    }

    //Todo don't get all date
    /**
     * Retrieves messages for a specific user from the API.
     *
     * @param id The ID of the user.
     * @return A CompletableFuture<List<Message>> that completes with the list of messages if successful.
     */
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
                // Handle failure case
            }
        });
        return future;
    }

    /**
     * Registers the Firebase token for push notifications.
     *
     * @param token    The Firebase token.
     * @param username The username of the user.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
    public CompletableFuture<Integer> registerFireBase(String token, String username) {
        ReqFireBase reqfirebase = new ReqFireBase(token, username);
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

    /**
     * Sends a Firebase token for deletion.
     *
     * @param username The username of the user.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
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

    /**
     * Adds a friend to the user's friend list.
     *
     * @param userFriend The username of the friend to add.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
    public CompletableFuture<Integer> AddFriend(String userFriend) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        FindFriendRequest findFriendRequest = new FindFriendRequest(userFriend);
        api.addUserFriend(findFriendRequest, token).enqueue(new Callback<UserGet.User>() {
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

    /**
     * Retrieves user data from the API.
     *
     * @param username The username of the user.
     * @return A CompletableFuture<DataUserRes> that completes with the user data if successful.
     */
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

    /**
     * Attempts to log in with the provided username and password.
     *
     * @param username The username.
     * @param password The password.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
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

    /**
     * Creates a new user with the provided information.
     *
     * @param username    The username.
     * @param password    The password.
     * @param displayName The display name.
     * @param profilePic  The URL of the profile picture.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
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

    /**
     * Deletes a friend with the specified friend ID.
     *
     * @param friendId The ID of the friend to delete.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
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

    /**
     * Sends a message to a friend using Firebase.
     *
     * @param message        The message to send.
     * @param friendUserName The username of the friend.
     * @return A CompletableFuture<Integer> that completes with the HTTP status code of the API response.
     */
    public CompletableFuture<Integer> sendMessageWithFirebase(Message message, String friendUserName) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        api.sendMessageWithFirebase(friendUserName, message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    Log.e("TAG", "onResponse: " + "Message sent successfully");
                    future.complete(200);
                } else {
                    Log.e("firebase", "Error: " + response.code());
                    future.complete(404);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("firebase", "Error: " + t.getMessage());
                future.complete(404);
            }
        });
        return future;
    }
}