package com.some.mvvmdemo.swipe;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemMoveListener itemMoveListener;

    public MyItemTouchHelperCallback(ItemMoveListener itemMoveListener){
        this.itemMoveListener = itemMoveListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        int flags = makeMovementFlags(dragFlags,swipeFlags);

        return flags;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return itemMoveListener.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        itemMoveListener.onItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    /**
     * 当item被拖拽或侧滑时触发
     *
     * @param viewHolder
     * @param actionState 当前item的状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //不管是拖拽或是侧滑，背景色都要变化
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(android.R.color.darker_gray));
        }
    }

    /**
     * 当item的交互动画结束时触发
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(android.R.color.white));
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleY(1);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                            float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive);

        //我们只需要在左右滑动时，将透明度和高度的值变小（1 --> 0）
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float value = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(value);
            viewHolder.itemView.setScaleY(value);
        }
    }
}
