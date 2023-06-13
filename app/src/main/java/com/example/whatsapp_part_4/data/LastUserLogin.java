package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Query;
@Dao

public interface LastUserLogin {
    @Query("SELECT * FROM LastUserLoginUser")
    String  getlastUserLogin();
    @Query("DELETE FROM LastUserLoginUser")
    void deleteAllUsers();
    @Query("INSERT INTO LastUserLoginUser VALUES(:user)")
    void insertUser(String user);

}
