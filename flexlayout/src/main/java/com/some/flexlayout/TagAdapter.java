package com.some.flexlayout;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.some.common.BindingViewHolder;
import com.some.flexlayout.databinding.ItemTagBinding;

import java.util.List;

/**
 * @author xiangxing
 */
public class TagAdapter  extends RecyclerView.Adapter<BindingViewHolder>{

    private static final String TAG = TagAdapter.class.getSimpleName();
    private List<String> mList;

    public TagAdapter(List<String> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        ItemTagBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_tag,parent,false);

        BindingViewHolder vHolder = new BindingViewHolder(binding);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BindingViewHolder holder, int position) {

        ItemTagBinding binding = (ItemTagBinding) holder.getBinding();
        binding.name.setText(mList.get(position));
        binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
