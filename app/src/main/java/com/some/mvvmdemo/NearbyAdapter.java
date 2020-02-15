package com.some.mvvmdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.some.mvvmdemo.databinding.ItemNearbyBinding;
import com.some.mvvmdemo.entity.Account;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NearbyAdapter.class.getSimpleName();
    private List<Account> mList;

    public NearbyAdapter(List<Account> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        ItemNearbyBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_nearby,parent,false);

        VHolder vHolder = new VHolder(binding);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        VHolder vHolder = (VHolder)holder;
        ItemNearbyBinding binding = (ItemNearbyBinding) vHolder.getBinding();
        binding.setAccount(mList.get(position));
        Log.d(TAG, "binding.tvName =  " + binding.tvName.getText());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class VHolder extends RecyclerView.ViewHolder{

        ViewDataBinding binding;

        public VHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
