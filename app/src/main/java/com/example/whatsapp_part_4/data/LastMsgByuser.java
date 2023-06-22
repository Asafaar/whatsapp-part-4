package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * LastMsgByuser is a Data Access Object (DAO) interface for performing database operations
 * on the LastMessageByUser entity.
 */
@Dao
public interface LastMsgByuser {

    /**
     * Retrieves the last message by user with the specified ID.
     *
     * @param id The ID of the user.
     * @return The LastMessageByUser object representing the last message by the user.
     */
    @Query("SELECT * FROM LastMessageByUser WHERE id = :id")
    LastMessageByUser getlastMessagesById(String id);

    /**
     * Updates the message of the LastMessageByUser with the specified ID.
     *
     * @param id         The ID of the user.
     * @param newMessage The new Message object to be updated.
     */
    @Query("UPDATE LastMessageByUser SET msgId = :newMessage WHERE id = :id")
    void updateMessageById(String id, Message newMessage);

    /**
     * Deletes the LastMessageByUser with the specified ID.
     *
     * @param id The ID of the user.
     */
    @Query("DELETE FROM LastMessageByUser WHERE id = :id")
    void deleteMessageById(String id);

    /**
     * Deletes all LastMessageByUser entries from the table.
     */
    @Query("DELETE FROM LastMessageByUser")
    void deleteAllUsers();
}
