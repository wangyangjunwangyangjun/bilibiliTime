package com.example.time.scollListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ScollListView extends ListView {
    private float mLastY;
    public ScollListView(Context context) {
        super(context);
    }
    public ScollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ScollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScollListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //重点在这里
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                super.onInterceptTouchEvent(ev);
                //不允许上层viewGroup拦截事件.
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //满足listView滑动到顶部，如果继续下滑，那就让scrollView拦截事件
                if (getFirstVisiblePosition() == 0 && (ev.getY() - mLastY) > 0) {
                    //允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                //满足listView滑动到底部，如果继续上滑，那就让scrollView拦截事件
                else if (getLastVisiblePosition() == getCount() - 1 && (ev.getY() - mLastY) < 0) {
                    //允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                //不允许上层viewGroup拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        mLastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }
}