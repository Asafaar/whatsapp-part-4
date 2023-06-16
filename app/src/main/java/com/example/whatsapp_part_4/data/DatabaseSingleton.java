package com.example.whatsapp_part_4.data;

import android.content.Context;

import androidx.room.Room;

import com.example.whatsapp_part_4.Model.Model;

/**
 * DatabaseSingleton is a singleton class that creates a single instance of the database
 * so from any place in the code, we can get the database instance and use it.
 */
public class DatabaseSingleton {
    private static Appdb db;
    private static Model model;

    /**
     * Returns the singleton instance of the Appdb database.
     *
     * @param context The application context.
     * @return The Appdb database instance.
     */
    public static synchronized Appdb getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), Appdb.class, "maind15")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }

    /**
     * Returns the singleton instance of the Model.
     *
     * @param context The application context.
     * @return The Model instance.
     */
    public static synchronized Model getModel(Context context) {
        if (model == null) {
            db = getDatabase(context);
            model = new Model(db);
        }
        return model;
    }
}
