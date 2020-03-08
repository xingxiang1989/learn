package com.some.mvvmdemo.swipe;

public interface ItemMoveListener {

    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemoved(int position);

}
