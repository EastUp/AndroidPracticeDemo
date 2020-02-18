package com.east.architect_zenghui.architect9_designmode2_builder.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.east.architect_zenghui.R;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 可以拿过来直接使用的 默认样式导航栏
 *  @author: East
 *  @date: 2020-02-17
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class DefaultNavigationBar extends AbsNavigationBar {
    public DefaultNavigationBar(Builder build) {
        super(build);
    }

    /**
     *  导航栏的Builder
     */
    public static class Builder extends AbsNavigationBar.Builder<Builder>{

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.ui_navigation_bar, parent);
        }

        public Builder setLeftText(String text){
            setText(R.id.back_tv,text);
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener clickListener){
            setOnClickListener(R.id.back_tv,clickListener);
            return this;
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }
    }
}
