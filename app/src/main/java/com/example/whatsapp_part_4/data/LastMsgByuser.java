package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LastMsgByuser {
    @Query("SELECT * FROM LastmessgeByuser WHERE id = :id")
    LastmessgeByuser getlastMessagesById(String id);
    @Query("UPDATE LastmessgeByuser SET msgid = :newMessage WHERE id = :id")
    void updateMessageById(String id, Message newMessage);
    @Query("DELETE FROM LastmessgeByuser WHERE id = :id")
    void deleteMessageById(String id);
    @Query("DELETE FROM LastmessgeByuser")
    void deleteAllUsers();
}
