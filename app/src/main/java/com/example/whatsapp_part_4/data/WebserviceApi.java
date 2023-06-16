package com.example.whatsapp_part_4.data;

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

/**
 * Represents a Retrofit API interface for making network requests to the Webservice.
 */
public interface WebserviceApi {
    /**
     * Creates a new user.
     *
     * @param userRequest   The user request object containing the user information.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @POST("Users")
    Call<Void> createUser(@Body UserRequest userRequest);

    /**
     * Retrieves user data for the given username.
     *
     * @param username      The username of the user to retrieve data for.
     * @param authorization The authorization token.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @GET("Users/{username}}")
    Call<DataUserRes> getUserData(@Path("username") String username, @Header("Authorization") String authorization);

    /**
     * Retrieves an authentication token.
     *
     * @param loginRequest  The login request object containing the login credentials.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @POST("Tokens")
    Call<String> getToken(@Body LoginRequest loginRequest);

    /**
     * Adds a friend to the user's chat.
     *
     * @param findFriendRequest The find friend request object containing the friend information.
     * @param authorization     The authorization token.
     * @return                  A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @POST("Chats")
    Call<UserGet.User> addUserFriend(@Body FindFriendRequest findFriendRequest, @Header("Authorization") String authorization);

    /**
     * Retrieves the user's friends.
     *
     * @param authorization The authorization token.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @GET("Chats")
    Call<List<UserGet>> getFriends(@Header("Authorization") String authorization);

    /**
     * Sends a message to a friend.
     *
     * @param friendId       The friend ID.
     * @param sendMsgRequest The send message request object containing the message content.
     * @param authorization  The authorization token.
     * @return               A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @POST("Chats/{friendId}/Messages")
    Call<Message> sendMessage(@Path("friendId") String friendId, @Body SendMessageRequest sendMsgRequest, @Header("Authorization") String authorization);

    /**
     * Retrieves messages from a friend.
     *
     * @param friendId      The friend ID.
     * @param authorization The authorization token.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @GET("Chats/{friendId}/Messages")
    Call<List<Message>> getMessage(@Path("friendId") String friendId, @Header("Authorization") String authorization);

    /**
     * Deletes a friend from the user's chat.
     *
     * @param friendId      The friend ID.
     * @param authorization The authorization token.
     * @return              A Retrofit Call object representing the API call.
     */
    @Headers("Content-Type: application/json")
    @DELETE("Chats/{friendId}")
    Call<Void> deleteFriend(@Path("friendId") String friendId, @Header("Authorization") String authorization);

    /**
     * Sends a Firebase token.
     *
     * @param reqfirebase  The Firebase request object containing the token information.
     * @return             A Retrofit Call object representing the API call.
     */
    @POST("tokenFirebase")
    Call<Void> sendTokenfirebase(@Body ReqFireBase reqfirebase);

    /**
     * Deletes a Firebase token.
     *
     * @param username     The username.
     * @return             A Retrofit Call object representing the API call.
     */
    @POST("tokenfirebase/delete")
    Call<Void> sendTokenfirebasedel(@Body JSONObject username);

    /**
     * Sends a message with Firebase.
     *
     * @param usernamefriend The friend username.
     * @param message        The message object containing the message content.
     * @return               A Retrofit Call object representing the API call.
     */
    @POST("msgfirebase/{usernamefriend}")
    @Headers("Content-Type: application/json")
    Call<Void> sendMessageWithFirebase(@Path("usernamefriend") String usernamefriend, @Body Message message);
}
