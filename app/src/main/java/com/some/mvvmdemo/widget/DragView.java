package com.some.mvvmdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.some.mvvmdemo.R;

public class DragView extends FrameLayout {

    private static final String TAG = DragView.class.getSimpleName();
    public DragView(Context context) {
        this(context,null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();
                float y = event.getY();

                float x1 = event.getRawX();
                float y1 = event.getRawY();


                Log.d(TAG," getX x =" + x + " y = " + y);
                Log.d(TAG," getRawX x1 =" + x1 + " y1 = " + y1);

                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return super.onTouchEvent(event);
    }
}
