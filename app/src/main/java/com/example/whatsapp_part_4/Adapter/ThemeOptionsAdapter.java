package com.example.whatsapp_part_4.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp_part_4.Dialog.ThemeOption;
import com.example.whatsapp_part_4.R;

import java.util.List;

public class ThemeOptionsAdapter extends RecyclerView.Adapter<ThemeOptionsAdapter.ThemeOptionViewHolder> {

    private List<ThemeOption> themeOptions;
    private ThemeOption selectedOption;

    private int selectedPosition = RecyclerView.NO_POSITION;

    private OnThemeOptionClickListener themeOptionClickListener;

    public ThemeOptionsAdapter(List<ThemeOption> themeOptions) {
        this.themeOptions = themeOptions;
    }

    public void setOnThemeOptionClickListener(OnThemeOptionClickListener listener) {
        this.themeOptionClickListener = listener;
    }
    public ThemeOption getSelectedOption() {
        return selectedOption;
    }


    @NonNull
    @Override
    public ThemeOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme_option, parent, false);
        return new ThemeOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeOptionViewHolder holder, int position) {
        ThemeOption themeOption = themeOptions.get(position);
        holder.bind(themeOption);
    }

    @Override
    public int getItemCount() {
        return themeOptions.size();
    }


    public interface OnThemeOptionClickListener {
        void onThemeOptionClick(ThemeOption themeOption);
    }

    class ThemeOptionViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ImageView iconImageView;

        ThemeOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.themeName);
            iconImageView = itemView.findViewById(R.id.themeIcon);

            // Set click listener for the theme option item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedPosition = position;
                        selectedOption = themeOptions.get(position);

                        notifyDataSetChanged();

//                        // Notify the listener of the theme option click
//                        Log.e("TAG", "onClick: " + "selectedOption" );
//                        if (themeOptionClickListener != null) {
//                            Log.e("TAG", "onClick: " + "selectedOption2" );
//                            themeOptionClickListener.onThemeOptionClick(selectedOption);
//                        }
                    }
                }
            });

        }

        void bind(ThemeOption themeOption) {
            nameTextView.setText(themeOption.getName());
            iconImageView.setImageResource(R.drawable.baseline_radio_button_unchecked_24);

//            iconImageView.setBackgroundColor(themeOption.getColorResId());
            // Customize the color of the theme icon background
            GradientDrawable drawable = (GradientDrawable) iconImageView.getBackground();
            drawable.setColor(themeOption.getColorResId());
            if (getAdapterPosition() == selectedPosition) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_200));
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }
}
