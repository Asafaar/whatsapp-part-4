package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for the Message entity.
 */
@Dao
public interface MessageDao {
    /**
     * Retrieves messages by ID.
     *
     * @param id The ID of the messages to retrieve.
     * @return A list of messages with the given ID.
     */
    @Query("SELECT * FROM Message WHERE id = :id")
    List<Message> getMessagesById(String id);

    /**
     * Retrieves all messages.
     *
     * @return A list of all messages.
     */
    @Query("SELECT * FROM Message")
    List<Message> getAllMessages();

    /**
     * Inserts messages into the database.
     *
     * @param messages The messages to insert.
     */
    @Insert
    void insertMessage(Message... messages);

    /**
     * Inserts a list of messages into the database.
     *
     * @param messages The list of messages to insert.
     */
    @Insert
    void insertAll(List<Message> messages);

    /**
     * Deletes all messages from the database.
     */
    @Query("DELETE FROM Message")
    void deleteAllMessages();

    /**
     * Deletes a message with the specified ID from the database.
     *
     * @param id The ID of the message to delete.
     */
    @Query("DELETE FROM Message WHERE id = :id")
    void deleteMessageById(String id);
}
