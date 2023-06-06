package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query ("SELECT * FROM User WHERE id = :id")
    User getUserById(String id);
    @Query("SELECT * FROM User")
    List<User> getAllUsers();
    @Insert
    void insertUser(User... user);
    @Insert
    void insertAll(List<User> users);
    @Update
    void updateUser(User... user);
    @Delete
    void deleteUser(User... user);
    @Query("DELETE FROM User")
    void deleteAllUsers();

}
