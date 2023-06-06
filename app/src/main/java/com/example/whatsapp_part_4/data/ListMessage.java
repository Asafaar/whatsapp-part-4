package com.example.whatsapp_part_4.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListMessage extends MutableLiveData<List<Message>> {

   public ListMessage(){
        super();
        setValue(new LinkedList<Message>());

   }
   public LiveData<List<Message>> getMessages(){
       return this;
   }

}
