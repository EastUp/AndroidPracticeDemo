package com.east.architect_zenghui.architect_37_mvp1.simple3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;


public class MainActivity extends AppCompatActivity implements UserInfoContract.UserInfoView{
    private TextView mUserInfoTv;
    private UserInfoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // OkHttp +RxJava + Retrofit 这样写代码行不行？ 1 ，2 ，
        mUserInfoTv = (TextView) findViewById(R.id.tv);

        // 调用 Presenter 的方法
        mPresenter = new UserInfoPresenter();
        mPresenter.attach(this);
        // 耗时操作
        mPresenter.getUsers("ed6b0f7f34dd8cf7b003e40691457175");
    }

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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
