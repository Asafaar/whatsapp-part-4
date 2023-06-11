package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ThemeString {
    @NonNull
    @PrimaryKey

    int theme;
    public ThemeString(int theme){
        this.theme=theme;
    }

    public int getTheme() {
        return theme;
    }
}
