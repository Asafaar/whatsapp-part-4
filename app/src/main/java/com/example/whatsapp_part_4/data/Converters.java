package com.example.whatsapp_part_4.data;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static Message.Sender fromJson(String json) {
        return gson.fromJson(json, Message.Sender.class);
    }

    @TypeConverter
    public static String toJson(Message.Sender sender) {

        return gson.toJson(sender);
    }
    @TypeConverter
    public static Message fromJsonm2(String json) {
        return gson.fromJson(json, Message.class);
    }

    @TypeConverter
    public static String toJsonm(Message message) {
        return gson.toJson(message);
    }
    @TypeConverter
    public static List<ChatEn.User> fromUserJson(String value) {
        Type listType = new TypeToken<List<ChatEn.User>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toUserJson(List<ChatEn.User> users) {
        return gson.toJson(users);
    }


    @TypeConverter
    public static UserGet.Message fromJsonz(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserGet.Message.class);
    }

    @TypeConverter
    public static String toJson(UserGet.Message message) {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    @TypeConverter
    public static String toUserString(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @TypeConverter
    public static UserGet.User fromJsonu(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserGet.User.class);
    }

    @TypeConverter
    public static String toJsonu(UserGet.User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}