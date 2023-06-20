package com.example.whatsapp_part_4.Async;

import android.os.AsyncTask;

import com.example.whatsapp_part_4.Model.Model;

/**
 * This class represents an asynchronous task for loading messages of a user from the database and API.
 * It extends the {@link android.os.AsyncTask} class and is used to perform background operations.
 */
public class AsyncTaskMessage extends AsyncTask<Void, Void, Void> {
    private Model model;
    private String userid;

    /**
     * Constructs a new instance of the AsyncTaskMessage class.
     *
     * @param model  The model instance used for loading messages.
     * @param userid The ID of the user whose messages need to be loaded.
     */
    public AsyncTaskMessage(Model model, String userid) {
        this.model = model;
        this.userid = userid;
    }

    /**
     * Background task that loads messages of a user from the database.
     *
     * @param voids The parameters passed to the task (not used in this case).
     * @return Always returns null.
     */
    @Override
    protected Void doInBackground(Void... voids) {
        model.loadMsgOfUserFromDb(userid);
        return null;
    }

    /**
     * Executed after the background task has completed.
     * This method is called on the main UI thread.
     *
     * @param aVoid The result of the background task (not used in this case).
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        model.loadMsgOfUserFromApi(userid);
    }
}
