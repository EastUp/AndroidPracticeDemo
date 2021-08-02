package com.dongnao.lsn8_example;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //1、动态注册
//        dynamicJavaTest();
//        dynamicJavaTest2(88);
        //2、 native线程
        testThread();
    }

    public void updateUI(){
        if (Looper.myLooper() == Looper.getMainLooper()){
            Toast.makeText(this,"更新UI",Toast.LENGTH_SHORT).show();
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,"更新UI",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    native  void dynamicJavaTest();
    native  int dynamicJavaTest2(int i);

    native void testThread();

}
