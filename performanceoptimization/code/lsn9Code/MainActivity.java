package com.dongnao.battery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注意动态权限

        Battery.addWhite(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
