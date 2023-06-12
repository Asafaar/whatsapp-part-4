package com.example.whatsapp_part_4.data;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebserviceApi {
    @Headers("Content-Type: application/json")
    @POST("Users")
    Call<Void> createUser(@Body UserRequest userRequest);
    @Headers("Content-Type: application/json")
    @GET("Users/{username}}")
    Call<DataUserRes> getUserData(@Path("username") String username,@Header("Authorization") String authorization);
    @Headers("Content-Type: application/json")
    @POST("Tokens")
    Call<String> getToken(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("Chats")
    Call<UserGet.User> addUserFrined(@Body FindFriendRequest findFriendRequest, @Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("Chats")
    Call<List<UserGet>> getFriends(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("Chats/{friendId}/Messages")
    Call<Message> sendMessage(@Path("friendId") String friendId, @Body SendMessageRequest sendMsgRequest, @Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("Chats/{friendId}/Messages")
    Call<List<Message>> getMessage(@Path("friendId") String friendId, @Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @DELETE("Chats/{friendId}")
    Call<Void> deleteFriend(@Path("friendId") String friendId, @Header("Authorization") String authorization);

    @POST("tokenfirebase")
    Call<Void> sendTokenfirebase(@Body reqfirebase reqfirebase);

    @POST("tokenfirebase/delete")
    Call<Void> sendTokenfirebasedel(@Body String username);

    @POST("msgfirebase/{usernamefriend}")
    @Headers("Content-Type: application/json")
    Call<Void> sendMessageWithFirebase(@Path("usernamefriend") String usernamefriend,@Body Message message);


}