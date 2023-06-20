package com.example.whatsapp_part_4.Async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.data.Message;
import com.example.whatsapp_part_4.data.UserMessage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//TODO javadoc
public class AsyncTaskMessege extends AsyncTask<Void, Void,Void> {
    Model model;
    public String userid;
    public AsyncTaskMessege(Model model, String userid) {
        this.model = model;
        this.userid = userid;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        model.loadMsgOfUserFromDb(userid);
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        model.loadMsgOfUserFromApi(userid);
    }
}