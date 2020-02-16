package com.some.mvvmdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class NearbyFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = NearbyFragment.class.getSimpleName();
    NearbyFragmentBinding binding;
    NearbyVM nearbyVM;
    NearbyAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ");
        nearbyVM = ViewModelProviders.of(this).get(NearbyVM.class);
        nearbyVM.initData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView ");
        binding = DataBindingUtil.inflate(inflater,R.layout.nearby_fragment,container,
                false);
//        binding.setEventListener(new EventListener());

        binding.setClick(this);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_remove:
                Toast.makeText(getActivity(),"点击remove",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add:
                nearbyVM.add();
                break;
        }
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged hidden = " + hidden);
    }


}
