package com.dongnao.jnitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static {
        // libnative-lib.so
        System.loadLibrary("native-lib");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 1、传递数组
//        int i[] = {11,22,33,44};
//        String j[] = {"动","脑"};
//        test(i,j);
//        Log.e("Java","int数组："+ Arrays.toString(i));

//  2、传递引用类型
//        Bean bean = new Bean();
//        passObject(bean,"天之道");

// 3、引用
//        invokeBean2Method2();
        invokeBean2Method2();


    }

    native int test(int[] i,String[] j);

    //传递java class 给native使用
    native void passObject(Bean bean,String str);


    native void invokeBean2Method();

    native void invokeBean2Method2();
}
