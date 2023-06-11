package com.example.whatsapp_part_4.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.w3c.dom.Comment;

@Database(entities = { Message.class, UserMessage.class, LastmessgeByuser.class,UserGet.class,ThemeString.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class Appdb extends RoomDatabase {
//    public abstract UserDao userDao();

    public abstract MessageDao messageDao();

    public abstract UserMessageConnectDao userMessageConnectDao();

    public abstract LastMsgByuser lastMsgByuser();

    public abstract UeserGet UeserGet();

    public abstract ThemeSave ThemeSave();

}
