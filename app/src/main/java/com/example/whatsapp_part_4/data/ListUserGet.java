package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

public class ListUserGet extends MutableLiveData<List<UserGet>> {

    public ListUserGet() {
        super();
        setValue(new LinkedList<UserGet>());

    }

    public LiveData<List<UserGet>> getUserGets() {
        return this;
    }

}

