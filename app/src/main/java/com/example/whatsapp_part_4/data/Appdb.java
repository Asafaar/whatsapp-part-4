package com.example.whatsapp_part_4.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Appdb is an abstract class that represents the Room database for the WhatsApp application.
 * It defines the entities and database version, and provides access to the DAOs for database operations.
 */
@Database(entities = {LastUserLoginUser.class,  Message.class, UserMessage.class, LastMessageByUser.class,UserGet.class,ThemeString.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class Appdb extends RoomDatabase {

    /**
     * Retrieves the DAO for Message entity.
     *
     * @return The MessageDao object for performing database operations on messages.
     */
    public abstract MessageDao messageDao();

    /**
     * Retrieves the DAO for UserMessageConnect entity.
     *
     * @return The UserMessageConnectDao object for performing database operations on user-message connections.
     */
    public abstract UserMessageConnectDao userMessageConnectDao();

    /**
     * Retrieves the DAO for LastMsgByUser entity.
     *
     * @return The LastMsgByuUer object for performing database operations on last messages by user.
     */
    public abstract LastMsgByuser lastMsgByuser();

    /**
     * Retrieves the DAO for UeserGet entity.
     *
     * @return The UeserGet object for performing database operations on user data.
     */
    public abstract UeserGet UeserGet();

    /**
     * Retrieves the DAO for ThemeSave entity.
     *
     * @return The ThemeSave object for performing database operations on theme data.
     */
    public abstract ThemeSave ThemeSave();

    /**
     * Retrieves the DAO for LastUserLogin entity.
     *
     * @return The LastUserLogin object for performing database operations on last user login data.
     */
    public abstract LastUserLogin LastUserLogin();
}
