package com.east.architect_zenghui.architect_37_mvp1.simple6;

import android.widget.TextView;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;
import com.east.architect_zenghui.architect_37_mvp1.simple6.base.BaseMvpActivity;
import com.east.architect_zenghui.architect_37_mvp1.simple6.inject.InjectPresenter;


// 这个地方可以放个泛型，方便 1 对 1 ，去掉了
public class MainActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.UserInfoView{
    private TextView mUserInfoTv;

    // 抛出一个问题留在这里，多 Presenter 怎么处理，dagger ，自己写 Dagger 处理

    // 一个 View 里面肯定会有多个 Presneter 的情况，怎么处理 Dagger 处理 有时间会补一下 ，自己写一个注入
    // 对个 new  自己手动去 attach  和 detach
    @InjectPresenter
    private UserInfoPresenter mPresenter1;
    @InjectPresenter
    private UserInfoPresenter mPresenter2;

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
        mPresenter2.getUsers("ed6b0f7f34dd8cf7b003e40691457175");
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
