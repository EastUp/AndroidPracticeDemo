package com.east.architect_zenghui.architect_37_mvp1.simple5;

import android.widget.TextView;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;
import com.east.architect_zenghui.architect_37_mvp1.simple5.base.BaseMvpActivity;


public class MainActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.UserInfoView{
    private TextView mUserInfoTv;

    // 抛出一个问题留在这里，多 Presenter 怎么处理，dagger ，自己写 Dagger 处理

    @Override
    public void onLoading() {
        // 加载进度条
    }

    @Override
    public void onError() {
        // 显示错误
    }

    @Override
    public void onSucceed(UserInfo userInfo) {
        // 成功 这个时候 Activity 有可能会被关闭掉，有可能会异常崩溃（一般不会）
        // 1. 可以判断界面还在不在(试一试)
        // 2. 采用解绑（通用）
        mUserInfoTv.setText(userInfo.toString());
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initData() {
        getPresenter().getUsers("ed6b0f7f34dd8cf7b003e40691457175");
    }

    @Override
    protected void initView() {
        mUserInfoTv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test);
    }
}
