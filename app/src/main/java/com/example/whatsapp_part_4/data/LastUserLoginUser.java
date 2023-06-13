package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;
@Entity
public class LastUserLoginUser {
    @PrimaryKey
    @NonNull
    String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
