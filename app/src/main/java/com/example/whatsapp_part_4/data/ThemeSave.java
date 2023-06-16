package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Data Access Object (DAO) for ThemeString entity.
 */
@Dao
public interface ThemeSave {
    /**
     * Retrieves the theme string from the database.
     *
     * @return The ThemeString object representing the current theme.
     */
    @Query("SELECT * FROM ThemeString")
    ThemeString getTheme();

    /**
     * Deletes the theme string from the database.
     */
    @Query("DELETE FROM ThemeString")
    void deleteTheme();

    /**
     * Inserts a new theme string into the database.
     *
     * @param theme The theme string to insert.
     */
    @Query("INSERT INTO ThemeString VALUES(:theme)")
    void insertTheme(String theme);

    /**
     * Inserts a new ThemeString object into the database.
     *
     * @param themeString The ThemeString object to insert.
     */
    @Insert
    void insert(ThemeString themeString);
}
