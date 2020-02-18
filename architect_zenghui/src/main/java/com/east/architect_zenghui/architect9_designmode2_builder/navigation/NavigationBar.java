package com.east.architect_zenghui.architect9_designmode2_builder.navigation;

import android.content.Context;
import android.view.ViewGroup;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 可以拿过来直接使用的 导航栏
 *  @author: East
 *  @date: 2020-02-17
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class NavigationBar extends AbsNavigationBar{

    public NavigationBar(Builder build) {
        super(build);
    }


    public static class Builder extends AbsNavigationBar.Builder<Builder>{

        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }

}
