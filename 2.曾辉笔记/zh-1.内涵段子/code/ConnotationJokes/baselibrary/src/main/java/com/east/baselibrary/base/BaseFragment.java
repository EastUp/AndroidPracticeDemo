package com.east.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.east.baselibrary.ioc.JaminIoc;


/**
 * description：
 * <p/>
 * Created by 曾辉 on 2016/10/20.
 * QQ：240336124
 * Email: 240336124@qq.com
 * Version：1.0
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity();
        rootView = View.inflate(context,getLayoutId(),null);

        // 加入注解
        JaminIoc.INSTANCE.bind(rootView,this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

}
