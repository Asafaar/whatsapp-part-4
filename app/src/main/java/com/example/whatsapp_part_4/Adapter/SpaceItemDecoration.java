package com.example.whatsapp_part_4.Adapter;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * SpaceItemDecoration is a RecyclerView ItemDecoration that adds spacing between items.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    /**
     * Constructs a SpaceItemDecoration with the specified spacing between items.
     *
     * @param spacing The spacing between items.
     */
    public SpaceItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    /**
     * Retrieves the spacing around the items and sets it in the outRect for each item view.
     *
     * @param outRect   The Rect object that defines the spacing for the item view.
     * @param view      The child view of the RecyclerView.
     * @param parent    The RecyclerView itself.
     * @param state     The current state of the RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        // Add top spacing only for the first item to avoid double spacing between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing;
        } else {
            outRect.top = 0;
        }
    }
}
