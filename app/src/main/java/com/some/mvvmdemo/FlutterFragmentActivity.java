package com.some.mvvmdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.base.BaseActiviy;
import com.some.mvvmdemo.databinding.ActivityFlutterFragmentBinding;

import io.flutter.embedding.android.FlutterFragment;

/**
 * Created by xiangxing5 on 2021/7/5.
 * Describe:
 */
public class FlutterFragmentActivity extends BaseActiviy {

    ActivityFlutterFragmentBinding binding;
    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    private FlutterFragment flutterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("ApplicationActivityLifecycle", "xiangxingtest FlutterFragmentActivity onCreate");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_flutter_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        flutterFragment = (FlutterFragment) fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT);

        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.createDefault();
            fragmentManager
                    .beginTransaction()
                    .add( R.id.fragment_container, flutterFragment, TAG_FLUTTER_FRAGMENT )
                    .commit();
        }

    }
}
