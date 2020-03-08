package com.some.mvvmdemo.swipe;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemDragListener {
    void onStartDrags(RecyclerView.ViewHolder viewHolder);
}
