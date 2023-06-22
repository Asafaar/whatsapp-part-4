package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

/**
 * ListUsers is a custom implementation of MutableLiveData that holds a list of User objects.
 * It provides convenience methods to access and modify the list of User objects.
 */
public class ListUsers extends MutableLiveData<List<User>> {

    /**
     * Constructs a new instance of ListUsers.
     * Initializes the underlying list with an empty LinkedList of User objects.
     */
    public ListUsers() {
        super();
        setValue(new LinkedList<>());
    }

    /**
     * Returns a LiveData object that represents the list of User objects.
     *
     * @return A LiveData object containing the list of User objects.
     */
    public LiveData<List<User>> getUsers() {
        return this;
    }
}