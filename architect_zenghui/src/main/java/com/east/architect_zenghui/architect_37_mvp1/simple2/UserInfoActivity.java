package com.east.architect_zenghui.architect_37_mvp1.simple2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;

/**
 * Created by hcDarren on 2018/1/1.
 * 个人信息
 */
public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.UserInfoView {
    private TextView mUserInfoTv;
    private UserInfoContract.UserInfoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // OkHttp +RxJava + Retrofit 这样写代码行不行？ 1 ，2 ，
        mUserInfoTv = (TextView) findViewById(R.id.tv);

        // 调用 Presenter 的方法
        mPresenter = new UserInfoPresenter(this);
        mPresenter.getUsers("ed6b0f7f34dd8cf7b003e40691457175");
        // 获取用户信息

        // 提交修改数据
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
        // 成功
        mUserInfoTv.setText(userInfo.toString());
    }
}
