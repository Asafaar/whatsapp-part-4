package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

public class ListUsers extends MutableLiveData<List<User>> {
    public ListUsers(){
        super();
        setValue(new LinkedList<User>());

    }
    public LiveData<List<User>> getUsers(){
        return this;
    }
}
