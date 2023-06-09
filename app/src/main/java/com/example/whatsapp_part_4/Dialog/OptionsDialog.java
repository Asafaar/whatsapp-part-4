package com.example.whatsapp_part_4.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class OptionsDialog {

    private Context context;
    private String[] options;
    private OnOptionSelectedListener optionSelectedListener;

    public OptionsDialog(Context context, String[] options) {
        this.context = context;
        this.options = options;
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.optionSelectedListener = listener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select an Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedOption = options[which];
                Toast.makeText(context, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
                if (optionSelectedListener != null) {
                    optionSelectedListener.onOptionSelected(selectedOption);
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnOptionSelectedListener {
        void onOptionSelected(String option);
    }
}
