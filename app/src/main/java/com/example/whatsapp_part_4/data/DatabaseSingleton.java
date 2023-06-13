package com.example.whatsapp_part_4.data;

import android.content.Context;

import androidx.room.Room;

import com.example.whatsapp_part_4.Model.Model;

/**
 * DatabaseSingleton is a singleton class that creates a single instance of the database so from any
 * place on the code we can get the database instance and use it.
 */
public class DatabaseSingleton {
    private static Appdb db;
    private static Model model;

    public static synchronized Appdb getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), Appdb.class, "maind14")
                    .allowMainThreadQueries().build();
        }
        return db;
    }

    public static synchronized Model getModel(Context context) {
        if (model == null) {
            db = getDatabase(context);
            model = new Model(db);
        }
        return model;
    }
}