package com.example.whatsapp_part_4.data;

import androidx.room.TypeConverter;

import com.example.whatsapp_part_4.data.Message;
import com.google.gson.Gson;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

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
    public static Message fromJsonm(String json) {
        return gson.fromJson(json, Message.class);
    }

    @TypeConverter
    public static String toJsonm(Message message) {
        return gson.toJson(message);
    }
}