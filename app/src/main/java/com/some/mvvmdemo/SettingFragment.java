package com.some.mvvmdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.some.mvvmdemo.databinding.FragmentMsgBinding;
import com.some.mvvmdemo.widget.LoadingView;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = SettingFragment.class.getSimpleName();

    private FragmentMsgBinding binding;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_msg,container,
                false);

        binding.setClick(this);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ");
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv){
            ViewStub viewStub = binding.viewstub.getViewStub();
            if(viewStub != null){
                emptyView = viewStub.inflate();
                emptyView.findViewById(R.id.btn).setOnClickListener(this);
            }else{
                emptyView.setVisibility(View.VISIBLE);
            }

        }else if(v.getId() == R.id.tv2){
            ViewStub viewStub = binding.viewstub.getViewStub();
            if(viewStub != null){
                emptyView = viewStub.inflate();
                emptyView.setVisibility(View.GONE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
        }else if(v.getId() == R.id.btn){
            Toast.makeText(getActivity(),"11",Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.tv_loading){
            ViewStub viewStub = binding.viewStubLoading.getViewStub();
            if(viewStub != null){
                LoadingView loadingView = (LoadingView) viewStub.inflate();
            }
        }
    }
}
