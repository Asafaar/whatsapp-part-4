package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserMessageConnectDao {
    @Insert
    void insert(UserMessage userMessage);
    @Update
    void update(UserMessage userMessage);
    @Query("SELECT messageId FROM user_messages WHERE userId = :userId")
    List<String> getMessageIdsForUser(String userId);
    @Query("DELETE FROM user_messages WHERE userId = :userId")
    void deleteMessagesForUser(String userId);
    @Query("DELETE FROM user_messages")
    void deleteAllMessages();
}
