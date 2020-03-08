package com.some.mvvmdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.some.mvvmdemo.databinding.ItemNearbyBinding;
import com.some.mvvmdemo.entity.Account;
import com.some.mvvmdemo.swipe.ItemDragListener;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private static final String TAG = NearbyAdapter.class.getSimpleName();
    private List<Account> mList;
    private ItemDragListener itemDragListener;

    public NearbyAdapter(List<Account> mList, ItemDragListener itemDragListener){
        this.mList = mList;
        this.itemDragListener = itemDragListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        ItemNearbyBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_nearby,parent,false);

        BindingViewHolder vHolder = new BindingViewHolder(binding);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BindingViewHolder holder, int position) {

        ItemNearbyBinding binding = (ItemNearbyBinding) holder.getBinding();
        binding.setAccount(mList.get(position));
        Log.d(TAG, "binding.tvName =  " + binding.tvName.getText());

        binding.tvDrag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                itemDragListener.onStartDrags(holder);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
