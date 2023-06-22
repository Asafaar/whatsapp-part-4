package com.example.whatsapp_part_4.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Adapter.ThemeOptionsAdapter;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.ConstantData;
import com.example.whatsapp_part_4.data.DatabaseSingleton;

import java.util.List;

/**
 * Dialog for displaying theme options and IP editing.
 */
public class OptionsDialog {

    private Context context;
    private List<ThemeOption> themeOptions;
    private OnOptionSelectedListener optionSelectedListener;

    public OptionsDialog(Context context, List<ThemeOption> themeOptions) {
        this.context = context;
        this.themeOptions = themeOptions;
    }

    /**
     * Sets the listener for option selection events.
     *
     * @param listener The listener to be set.
     */
    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.optionSelectedListener = listener;
    }

    /**
     * Displays the options dialog.
     */
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Theme And Edit IP");
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_theme_options, null);

        RecyclerView recyclerView = dialogView.findViewById(R.id.optionsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        EditText editText = dialogView.findViewById(R.id.editTextIP);
        editText.setText(ConstantData.BASE_URL);

        ThemeOptionsAdapter adapter = new ThemeOptionsAdapter(themeOptions);
        adapter.setOnThemeOptionClickListener(themeOption -> {
            if (optionSelectedListener != null) {
                optionSelectedListener.onOptionSelected(themeOption);
            }
        });
        recyclerView.setAdapter(adapter);

        builder.setView(dialogView);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Accept", (dialog, which) -> {
            // Find the selected theme option
            ThemeOption selectedOption = adapter.getSelectedOption();
            if (!editText.getText().toString().equals(ConstantData.BASE_URL)){
                Log.e("TAG", "show: "+editText.getText().toString() );
                ConstantData.BASE_URL = editText.getText().toString();
                DatabaseSingleton.getModel(context).setRetrofit(context,editText.getText().toString());

            }
            Log.e("TAG", "show: "+ConstantData.BASE_URL );
            if (selectedOption != null && optionSelectedListener != null) {
                optionSelectedListener.onOptionSelected(selectedOption);
            }
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Listener interface for option selection events.
     */
    public interface OnOptionSelectedListener {
        /**
         * Called when a theme option is selected.
         *
         * @param themeOption The selected theme option.
         */
        void onOptionSelected(ThemeOption themeOption);
    }
}
