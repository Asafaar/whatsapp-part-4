package com.example.whatsapp_part_4.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing the theme string stored in the database.
 */
@Entity
public class ThemeString {
    @PrimaryKey
    private int theme;

    /**
     * Constructs a new ThemeString object.
     *
     * @param theme The theme string value.
     */
    public ThemeString(int theme) {
        this.theme = theme;
    }

    /**
     * Retrieves the theme string.
     *
     * @return The theme string value.
     */
    public int getTheme() {
        return theme;
    }
}
