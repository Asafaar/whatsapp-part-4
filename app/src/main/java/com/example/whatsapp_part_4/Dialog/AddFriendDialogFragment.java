package com.example.whatsapp_part_4.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.whatsapp_part_4.Model.Model;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.DatabaseSingleton;

public class AddFriendDialogFragment extends DialogFragment {
    private EditText mEditText;
    // Use this instance of the interface to deliver action events
    private AddFriendDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddFriendDialogListener so we can send events to the host
            mListener = (AddFriendDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context + " must implement AddFriendDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because it's going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_add_friend, null);
        mEditText = view.findViewById(R.id.edit_text_friend_name);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String friend = mEditText.getText().toString();
                        // Send the positive button event back to the host activity
                        Model model = DatabaseSingleton.getModel(getContext());
                        model.addNewFriend(friend).thenAccept(status -> {
                            Log.i("Status = ", String.valueOf(status));
                            if (status == -2) {
                                showToast("Can't add yourself");
                            } else if (status == -3) {
                                showToast("You and " + friend + " are already friends");
                            } else if (status == -1) {
                                showToast("The user " + friend + "doesn't exist");
                            } else {
                                showToast("You and " + friend + " are now friends!");
                                mListener.onAddFriendDialogPositiveClick(friend);
                            }
                        });
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        AddFriendDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    public interface AddFriendDialogListener {
        void onAddFriendDialogPositiveClick(String friendName);
    }
}