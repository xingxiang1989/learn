package com.some.mvvmdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.some.mvvmdemo.databinding.NearbyFragmentBinding;
import com.some.mvvmdemo.entity.Account;

import java.util.List;

public class NearbyFragment extends Fragment {

    NearbyFragmentBinding binding;
    NearbyVM nearbyVM;
    NearbyAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearbyVM = ViewModelProviders.of(this).get(NearbyVM.class);
        nearbyVM.initData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.nearby_fragment,container,
                false);
        binding.setEventListener(new EventListener());

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL,false);
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nearbyVM.getLiveData().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                if(adapter == null){
                    adapter = new NearbyAdapter(nearbyVM.getLiveData().getValue());
                    binding.recyclerview.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    public class EventListener{

        public void onClick(View view){

            Toast.makeText(getActivity(),"点击",Toast.LENGTH_SHORT).show();

            switch (view.getId()){
                case R.id.btn_add:
                    nearbyVM.add();
                    break;
            }

        }
    }


}
