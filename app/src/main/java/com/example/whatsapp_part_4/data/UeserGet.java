package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import java.util.List;
@Dao
public interface UeserGet {
    @Query("SELECT * FROM UserGet WHERE id = :id")
    UserGet getUserById(String id);
    @Query("SELECT * FROM UserGet")
    List<UserGet> getAllUsers();
    @Insert
    void insertUser(UserGet... user);
    @Insert
    void insertAll(List<UserGet> users);
    @Update
    void updateUser(UserGet... userget);

    @Query("DELETE FROM UserGet WHERE id = :id")
    void deleteUserById(String id);
    @Delete
    void deleteUser(UserGet... user);
    @Query("DELETE FROM UserGet")
    void deleteAllUsers();
}
