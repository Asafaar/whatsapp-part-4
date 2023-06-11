package com.example.whatsapp_part_4.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Adapter.ThemeOptionsAdapter;
import com.example.whatsapp_part_4.R;
import com.example.whatsapp_part_4.data.ConstantData;
import com.example.whatsapp_part_4.data.DatabaseSingleton;

import java.util.List;

public class OptionsDialog {

    private Context context;
    private List<ThemeOption> themeOptions;
    private OnOptionSelectedListener optionSelectedListener;

    public OptionsDialog(Context context, List<ThemeOption> themeOptions) {
        this.context = context;
        this.themeOptions = themeOptions;
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.optionSelectedListener = listener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Theme And Edit Ip");
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_theme_options, null);

        RecyclerView recyclerView = dialogView.findViewById(R.id.optionsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        EditText editText=dialogView.findViewById(R.id.editTextIP);
        editText.setText(ConstantData.BASE_URL);

        ThemeOptionsAdapter adapter = new ThemeOptionsAdapter(themeOptions);
        adapter.setOnThemeOptionClickListener(new ThemeOptionsAdapter.OnThemeOptionClickListener() {
            @Override
            public void onThemeOptionClick(ThemeOption themeOption) {
                if (optionSelectedListener != null) {
                    Log.e("TAG", "onThemeOptionClick: " + themeOption.getName() );
                    optionSelectedListener.onOptionSelected(themeOption);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        builder.setView(dialogView);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Find the selected theme option
                ThemeOption selectedOption = adapter.getSelectedOption();
                ConstantData.BASE_URL=editText.getText().toString();
                DatabaseSingleton.getModel(context).setRetrofit(ConstantData.BASE_URL);
                if (selectedOption != null && optionSelectedListener != null) {
                    Log.e("TAG", "onClick: " + selectedOption.getName() );
                    optionSelectedListener.onOptionSelected(selectedOption);
                    dialog.dismiss();

                }
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnOptionSelectedListener {
        void onOptionSelected(ThemeOption themeOption);
    }
}
