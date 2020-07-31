package com.example.time.myGridView;

import android.widget.GridView;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.RequiresApi;

public class MyGridView extends GridView {

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        int heightSpec;
        if(getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        else{
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }


}
