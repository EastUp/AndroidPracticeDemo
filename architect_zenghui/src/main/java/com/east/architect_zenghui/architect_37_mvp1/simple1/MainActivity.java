package com.east.architect_zenghui.architect_37_mvp1.simple1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.BaseSubscriber;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.RetrofitClient;
import com.east.architect_zenghui.architect_37_mvp1.retrofit.UserInfo;


public class MainActivity extends AppCompatActivity {
    private TextView mUserInfoTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // OkHttp +RxJava + Retrofit 这样写代码行不行？ 1 ，2 ，
        mUserInfoTv = (TextView) findViewById(R.id.tv);
        // 1. MVC 两个地方：个人主页，编辑资料，MVC意味着 ，这些代码是需要写很多份
        // 2. 如果团队协作，多人开发，那么这个页面（编辑资料）一般都是一个人在做，项目比较紧凑的时候，不好分配人
        // 3. 如果某些界面需求变更的情况下，不好定位，或者说出了 Bug 的情况下不怎么好修改（代码多）
        RetrofitClient.getServiceApi()
                .queryUserInfo("5daefee2ce7f9208d465fab4ae6e40c2")
                // .subscribeOn().observeOn().subscribe()
                // Subscriber 封装一下
                // 第二个坑 , 坑我们 返回值都是一个泛型，转换返回值泛型
                .compose(RetrofitClient.<UserInfo>transformer())
                // 注册完了要登录
                .subscribe(new BaseSubscriber<UserInfo>() {
                    @Override
                    protected void onError(String errorCode, String errorMessage) {
                        toast(errorMessage);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        // 这个处理代码不一样
                        mUserInfoTv.setText(userInfo.toString());
                    }
                });
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void log(String text) {
        Log.e("TAG->Result", text);
    }
}
