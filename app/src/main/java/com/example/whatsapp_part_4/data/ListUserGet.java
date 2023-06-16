package com.example.whatsapp_part_4.data;

import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

/**
 * ListUserGet is a custom implementation of MutableLiveData that holds a list of UserGet objects.
 * It provides convenience methods to access and modify the list of UserGet objects.
 */
public class ListUserGet extends MutableLiveData<List<UserGet>> {

    /**
     * Constructs a new instance of ListUserGet.
     * Initializes the underlying list with an empty LinkedList of UserGet objects.
     */
    public ListUserGet() {
        super();
        setValue(new LinkedList<>());
    }
}