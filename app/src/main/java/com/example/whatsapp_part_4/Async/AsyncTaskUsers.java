package com.example.whatsapp_part_4.Async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.data.DatabaseSingleton;
import com.example.whatsapp_part_4.data.UserGet;

import java.lang.ref.WeakReference;
import java.util.List;

public class AsyncTaskUsers extends AsyncTask<Void, Void, Void> {
    private Model model;


    public AsyncTaskUsers( Model model) {
        this.model = model;
        }

    @Override
    protected Void doInBackground(Void... voids) {
         loadMessagesFromDatabase();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        model.reloadusersOntheback();

    }

    private void loadMessagesFromDatabase() {

        model.reloadusergetfromdb();

    }




}
