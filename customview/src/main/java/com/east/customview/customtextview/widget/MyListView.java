package com.east.customview.customtextview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/5/13.
 * Version 1.0
 * Description:
 */
public class MyListView extends ListView{

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 解决显示不全的问题  32值
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        // heightMeasureSpec 32位的值  30 是 Integer.MAX_VALUE   2位是  MeasureSpec.AT_MOST
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
