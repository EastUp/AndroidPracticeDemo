package com.east.architect_zenghui.architect9_designmode2_builder.navigation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 导航栏的基类
 *  @author: East
 *  @date: 2020-02-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class AbsNavigationBar implements INavigation{

    private Builder mBuilder;
    private View mNavigationBarView;

    public AbsNavigationBar(Builder build) {
        this.mBuilder = build;

        if(mBuilder.mParent == null){
            // 获取activity的根布局，View源码
            ViewGroup activityRoot = (ViewGroup) ((Activity)(mBuilder.mContext))
                    .getWindow().getDecorView();  //DecorView是FrameLayout
            mBuilder.mParent = (ViewGroup) activityRoot.getChildAt(0);  //获取到的是LinearLayout
            //android.R.id.content获取到的是LinearLayout中的FrameLayout
        }
        //创建NavigationView
         mNavigationBarView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mLayoutId,mBuilder.mParent,false);
        //添加到Parent中,放在最顶层
        mBuilder.mParent.addView(mNavigationBarView,0);
        attachNavigationParams();
    }

    /**
     * 返回 Builder
     * @return
     */
    public Builder getmBuilder() {
        return mBuilder;
    }

    /**
     *  绑定参数
     */
    @Override
    public void attachNavigationParams() {
        //设置Text
        Set<Map.Entry<Integer,CharSequence>> set = mBuilder.mTextMap.entrySet();
        for (Map.Entry<Integer, CharSequence> entry : set) {
            TextView textView = mNavigationBarView.findViewById(entry.getKey());
            textView.setText(entry.getValue());
        }

        //设置Click时间
        Set<Map.Entry<Integer, View.OnClickListener>> clickSet = mBuilder.mClickListenerMap.entrySet();
        for (Map.Entry<Integer, View.OnClickListener> clickListenerEntry : clickSet) {
            View view = mNavigationBarView.findViewById(clickListenerEntry.getKey());
            view.setOnClickListener(clickListenerEntry.getValue());
        }
    }

    /**
     *  Builder设计模式中的Builder
     */
    public static abstract class Builder<B extends Builder>{
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public Map<Integer,CharSequence> mTextMap = new HashMap<>();
        public Map<Integer, View.OnClickListener> mClickListenerMap = new HashMap<>();

        public Builder(Context context,int layoutId,ViewGroup parent){
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;
        }

        public B setText(int viewId, CharSequence charSequence){
            mTextMap.put(viewId,charSequence);
            return (B)this;
        }

        public B setOnClickListener(int viewId, View.OnClickListener clickListener){
            mClickListenerMap.put(viewId,clickListener);
            return (B)this;
        }


        public abstract AbsNavigationBar create();

    }

}