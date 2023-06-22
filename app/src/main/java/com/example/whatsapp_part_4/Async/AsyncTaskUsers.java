package com.example.whatsapp_part_4.Async;

import android.os.AsyncTask;

import com.example.whatsapp_part_4.Model.Model;

/**
 * AsyncTaskUsers is an AsyncTask class that performs background operations to load user data from the database.
 */
public class AsyncTaskUsers extends AsyncTask<Void, Void, Void> {
    private Model model;

    /**
     * Constructs an AsyncTaskUsers object.
     *
     * @param model The Model object used to access the database and update user data.
     */
    public AsyncTaskUsers(Model model) {
        this.model = model;
    }

    /**
     * Performs the background operation of loading user data from the database.
     *
     * @param voids The parameters passed to the AsyncTask (not used in this case).
     * @return null
     */
    @Override
    protected Void doInBackground(Void... voids) {
        loadMessagesFromDatabase();
        return null;
    }

    /**
     * Called after the background operation is completed.
     * Reloads the user data using the Model object.
     *
     * @param aVoid The result of the background operation (not used in this case).
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        model.reloadUsersInTheBack();
    }

    /**
     * Loads user data from the database using the Model object.
     */
    private void loadMessagesFromDatabase() {
        model.reloadUserGetFromDb();
    }
}
