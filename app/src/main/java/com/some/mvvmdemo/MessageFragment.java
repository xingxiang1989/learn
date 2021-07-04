package com.some.mvvmdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.some.common.arouter.RouterUrl;
import com.some.hookactivity.UnRegisterActivity;
import com.some.mvvmdemo.aidl.AIDLActivity;
import com.some.mvvmdemo.base.BaseFragment;
import com.some.mvvmdemo.databinding.FragmentMsgBinding;
import com.some.mvvmdemo.file.FileActivity;
import com.some.mvvmdemo.hilt.HiltActivity;
import com.some.mvvmdemo.http.RetrofitTestActivity;
import com.some.mvvmdemo.inflate.LayoutInflaterActivity;
import com.some.mvvmdemo.mvvm.ShareDataVM;
import com.some.mvvmdemo.proxy.DynamicProxyActivity;
import com.some.mvvmdemo.reflect.ReflectActivity;
import com.some.mvvmdemo.slideconflict.SlideConfictActivity;
import com.some.mvvmdemo.testkotlin.TestKotlinActivity;
import com.some.mvvmdemo.touch.TouchActivity;
import com.some.mvvmdemo.view.CakeViewActivity;
import com.some.mvvmdemo.view.FiveRingsActivity;
import com.some.mvvmdemo.view.MultiTouchActivity;
import com.some.mvvmdemo.view.RouletteViewActivity;
import com.some.mvvmdemo.view.TimeLineActivity;
import com.some.mvvmdemo.viewpager2.ViewPager2Activity;

public class MessageFragment extends BaseFragment {

    private static final String TAG = "xingtest-MessageFragment";
    FragmentMsgBinding binding;
    ShareDataVM shareDataVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_msg,container,
                false);

        binding.tv.setText("MessageFragment");

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
        shareDataVM = ViewModelProviders.of(getActivity()).get(ShareDataVM.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated ");
        shareDataVM.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onViewCreated onChanged ");
                binding.contentTv.setText(s);
            }
        });

        binding.retrofitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, RetrofitTestActivity.class));
            }
        });

        binding.ringTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick to FiveRingsActivity ");
                LogUtils.d("xiangxingtest taskid = " + getActivity().getTaskId());

//                startActivity(new Intent(mActivity, FiveRingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                startActivity(new Intent(mActivity, FiveRingsActivity.class));

            }
        });

        binding.cakeViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick to CakeViewActivity ");

                startActivity(new Intent(mActivity, CakeViewActivity.class));
            }
        });

        binding.cakeViewTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick to CakeViewActivity2 ");

                startActivity(new Intent(mActivity, RouletteViewActivity.class));
            }
        });

        binding.timeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TimeLineActivity.class));
            }
        });

        binding.multiTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MultiTouchActivity.class));
            }
        });

        binding.viewpager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onclick to ViewPager2Activity ");
                startActivity(new Intent(mActivity, ViewPager2Activity.class));
            }
        });

        binding.conflict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SlideConfictActivity.class));
            }
        });

        binding.dispatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TouchActivity.class));
            }
        });

        binding.aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AIDLActivity.class));
            }
        });

        binding.countDownLatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TestKotlinActivity.class));
            }
        });

        binding.reflect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ReflectActivity.class));
            }
        });

        binding.inflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, LayoutInflaterActivity.class));
            }
        });

        binding.hook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HookUtils.hookAMSAidl();
                startActivity(new Intent(mActivity, UnRegisterActivity.class));
            }
        });

        binding.flexbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mActivity, FlexLayoutActivity.class));
                ARouter.getInstance().build(RouterUrl.flexOut).navigation();
            }
        });

        binding.jni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, JniTestActivity.class));
            }
        });

        binding.oom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, OOMTestActivity.class));
            }
        });

        binding.hilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, HiltActivity.class));
            }
        });

        binding.proxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, DynamicProxyActivity.class));
            }
        });

        binding.file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, FileActivity.class));
            }
        });

        binding.encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AlgorithmActivity.class));
            }
        });

        binding.changeSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ChangeSkinActivity.class));

            }
        });

        binding.flutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ChangeSkinActivity.class));

            }
        });
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint isVisibleToUser = " + isVisibleToUser);
    }
}
