package com.east.architect_zenghui.architect_37_mvp1.simple5.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by hcDarren on 2018/1/1.
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        // 创建 P，创建只能交给 子类，每个 Activity 都不一样
        mPresenter = createPresenter();
        mPresenter.attach(this);

        initView();
        initData();
    }

    // 由子类去实现创建
    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public P getPresenter() {
        return mPresenter;
    }
}
