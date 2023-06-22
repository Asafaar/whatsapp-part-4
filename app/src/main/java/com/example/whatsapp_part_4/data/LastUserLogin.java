package com.example.whatsapp_part_4.data;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * LastUserLogin is a Data Access Object (DAO) interface for performing database operations
 * on the LastUserLoginUser entity.
 */
@Dao
public interface LastUserLogin {

    /**
     * Retrieves the last user login from the LastUserLoginUser table.
     *
     * @return The last user login as a string.
     */
    @Query("SELECT * FROM LastUserLoginUser")
    String getlastUserLogin();

    /**
     * Deletes all users from the LastUserLoginUser table.
     */
    @Query("DELETE FROM LastUserLoginUser")
    void deleteAllUsers();

    /**
     * Inserts a user into the LastUserLoginUser table.
     *
     * @param user The user to be inserted.
     */
    @Query("INSERT INTO LastUserLoginUser VALUES(:user)")
    void insertUser(String user);
}
