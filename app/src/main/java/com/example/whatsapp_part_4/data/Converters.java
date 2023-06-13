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
//        Log.e("19", "fromJson: "+json );
        return gson.fromJson(json, Message.Sender.class);
    }

    @TypeConverter
    public static String toJson(Message.Sender sender) {
//        Log.e("25", "toJson:Message.Sender "+sender.getProfilePic() );
//        Log.e("26", "toJson:Message.Sender "+sender.getDisplayName() );

        return gson.toJson(sender);
    }
    @TypeConverter
    public static Message fromJsonm2(String json) {
//        Log.e("32", "fromJsonm:Message "+json );
        return gson.fromJson(json, Message.class);
    }

    @TypeConverter
    public static String toJsonm(Message message) {
//        Log.e("38", "toJsonm: "+message );
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