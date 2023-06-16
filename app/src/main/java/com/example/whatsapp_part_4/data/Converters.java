package com.example.whatsapp_part_4.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

/**
 * Converters is a class that provides type conversion methods for Room database.
 * It uses Gson library to convert objects to JSON strings and vice versa.
 */
public class Converters {
    private static final Gson gson = new Gson();

    /**
     * Converts a JSON string to a Message.Sender object.
     *
     * @param json The JSON string to convert.
     * @return The Message.Sender object.
     */
    @TypeConverter
    public static Message.Sender fromJson(String json) {
        return gson.fromJson(json, Message.Sender.class);
    }

    /**
     * Converts a Message.Sender object to a JSON string.
     *
     * @param sender The Message.Sender object to convert.
     * @return The JSON string.
     */
    @TypeConverter
    public static String toJson(Message.Sender sender) {
        return gson.toJson(sender);
    }

    /**
     * Converts a JSON string to a Message object.
     *
     * @param json The JSON string to convert.
     * @return The Message object.
     */
    @TypeConverter
    public static Message fromJsonm2(String json) {
        return gson.fromJson(json, Message.class);
    }

    /**
     * Converts a Message object to a JSON string.
     *
     * @param message The Message object to convert.
     * @return The JSON string.
     */
    @TypeConverter
    public static String toJsonm(Message message) {
        return gson.toJson(message);
    }

    /**
     * Converts a JSON string to a UserGet.Message object.
     *
     * @param json The JSON string to convert.
     * @return The UserGet.Message object.
     */
    @TypeConverter
    public static UserGet.Message fromJsonz(String json) {
        return gson.fromJson(json, UserGet.Message.class);
    }

    /**
     * Converts a UserGet.Message object to a JSON string.
     *
     * @param message The UserGet.Message object to convert.
     * @return The JSON string.
     */
    @TypeConverter
    public static String toJson(UserGet.Message message) {
        return gson.toJson(message);
    }

    /**
     * Converts a JSON string to a UserGet.User object.
     *
     * @param json The JSON string to convert.
     * @return The UserGet.User object.
     */
    @TypeConverter
    public static UserGet.User fromJsonu(String json) {
        return gson.fromJson(json, UserGet.User.class);
    }

    /**
     * Converts a UserGet.User object to a JSON string.
     *
     * @param user The UserGet.User object to convert.
     * @return The JSON string.
     */
    @TypeConverter
    public static String toJsonu(UserGet.User user) {
        return gson.toJson(user);
    }
}
