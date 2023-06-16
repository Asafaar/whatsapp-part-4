package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

/**
 * ListMessage is a custom implementation of MutableLiveData that holds a list of messages.
 * It provides convenience methods to access and modify the list of messages.
 */
public class ListMessage extends MutableLiveData<List<Message>> {

    /**
     * Constructs a new instance of ListMessage.
     * Initializes the underlying list with an empty LinkedList of messages.
     */
    public ListMessage() {
        super();
        setValue(new LinkedList<>());
    }

    /**
     * Returns a LiveData object that represents the list of messages.
     *
     * @return A LiveData object containing the list of messages.
     */
    public LiveData<List<Message>> getMessages() {
        return this;
    }
}
