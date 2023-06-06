package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MessageDao {
    @Query("SELECT * FROM Message WHERE id = :id")
    List<Message> getMessagesById(String id);
    @Query("SELECT * FROM Message")
    List<Message> getAllMessages();

    @Insert
    void insertMessage(Message... messages);
    @Insert
    void insertAll(List<Message> Message);
    @Update
    void updateMessage(Message... messages);
    @Delete
    void deleteMessager(Message... messages);
    @Query("DELETE FROM Message")
    void deleteAllMessages();

    @Query("DELETE FROM Message WHERE id = :id")
    void deleteMessageById(String id);
}

