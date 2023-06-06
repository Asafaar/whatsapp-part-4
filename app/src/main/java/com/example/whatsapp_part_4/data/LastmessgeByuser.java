package com.example.whatsapp_part_4.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LastmessgeByuser {
    @PrimaryKey
    @NonNull
    String id;
    Message msgid;

    public String getId() {
        return id;
    }

    public Message getMsgid() {
        return msgid;
    }
}
