package com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;
import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1.RetrofitClient;
import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1.UserInfo;
import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2.HttpCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient.getServiceApi().userLogin1("","")
                .enqueue(new HttpCallback<UserInfo>() {
                    @Override
                    public void onSucceed(UserInfo result) {

                    }

                    @Override
                    public void onError(String code, String msg) {

                    }
                });
    }
}
