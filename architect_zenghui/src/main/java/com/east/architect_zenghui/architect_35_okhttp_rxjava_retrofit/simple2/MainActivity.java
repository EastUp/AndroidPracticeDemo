package com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1.RetrofitClient;
import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1.UserInfo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OkHttp +RxJava + Retrofit 这样写代码行不行？ 1 ，2 ，
        RetrofitClient.getServiceApi()
                .userLogin("14726932514", "123456")
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
                        Integer.parseInt(userInfo.userName);// 不会停止运行
                        // toast(userInfo.toString());
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
