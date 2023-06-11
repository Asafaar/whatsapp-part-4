package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ThemeSave {
    @Query("SELECT * FROM ThemeString")
    ThemeString getTheme();
    @Query("DELETE FROM ThemeString")
    void deleteTheme();
    @Query("INSERT INTO ThemeString VALUES(:theme)")
    void insertTheme(String theme);
    @Insert
    void insert(ThemeString themeString);
}
