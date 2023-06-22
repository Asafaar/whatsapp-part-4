package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for UserMessage entity.
 */
@Dao
public interface UserMessageConnectDao {
    /**
     * Inserts a UserMessage object into the database.
     *
     * @param userMessage The UserMessage to be inserted.
     */
    @Insert
    void insert(UserMessage userMessage);

    /**
     * Updates a UserMessage object in the database.
     *
     * @param userMessage The UserMessage to be updated.
     */
    @Update
    void update(UserMessage userMessage);

    /**
     * Retrieves a list of message IDs for a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of message IDs.
     */
    @Query("SELECT messageId FROM user_messages WHERE userId = :userId")
    List<String> getMessageIdsForUser(String userId);

    /**
     * Deletes all user messages for a given user ID.
     *
     * @param userId The ID of the user.
     */
    @Query("DELETE FROM user_messages WHERE userId = :userId")
    void deleteMessagesForUser(String userId);

    /**
     * Deletes all user messages from the database.
     */
    @Query("DELETE FROM user_messages")
    void deleteAllMessages();


}
