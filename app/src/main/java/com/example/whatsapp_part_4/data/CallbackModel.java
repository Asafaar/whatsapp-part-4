package com.example.whatsapp_part_4.data;

public interface CallbackModel {
   public int status = 0;
    void onSuccess();
    void onError(int statusCode);

    int hasError();
}
