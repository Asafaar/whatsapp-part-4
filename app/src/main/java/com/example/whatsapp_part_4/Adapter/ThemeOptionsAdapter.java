package com.example.whatsapp_part_4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

/**
 * ThemeOptionsAdapter is a RecyclerView adapter that displays a list of theme options.
 */
public class ThemeOptionsAdapter extends RecyclerView.Adapter<ThemeOptionsAdapter.ThemeOptionViewHolder> {

    private List<ThemeOption> themeOptions;
    private ThemeOption selectedOption;

    private int selectedPosition = RecyclerView.NO_POSITION;

    /**
     * Constructs a ThemeOptionsAdapter with the specified list of theme options.
     *
     * @param themeOptions The list of theme options to display.
     */
    public ThemeOptionsAdapter(List<ThemeOption> themeOptions) {
        this.themeOptions = themeOptions;
    }

    /**
     * Sets the click listener for theme options.
     *
     * @param listener The click listener for theme options.
     */
    public void setOnThemeOptionClickListener(OnThemeOptionClickListener listener) {
    }

    /**
     * Retrieves the currently selected theme option.
     *
     * @return The currently selected theme option.
     */
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

    /**
     * Interface definition for a callback to be invoked when a theme option is clicked.
     */
    public interface OnThemeOptionClickListener {
        /**
         * Called when a theme option is clicked.
         *
         * @param themeOption The clicked theme option.
         */
        void onThemeOptionClick(ThemeOption themeOption);
    }

    class ThemeOptionViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ImageView iconImageView;

        @SuppressLint("NotifyDataSetChanged")
        ThemeOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.themeName);
            iconImageView = itemView.findViewById(R.id.themeIcon);

            // Set click listener for the theme option item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position;
                    selectedOption = themeOptions.get(position);
                    notifyDataSetChanged();
                }
            });
        }

        /**
         * Binds the data of a theme option to the view holder.
         *
         * @param themeOption The theme option to bind.
         */
        void bind(ThemeOption themeOption) {
            nameTextView.setText(themeOption.getName());
            iconImageView.setImageResource(R.drawable.baseline_radio_button_unchecked_24);

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
