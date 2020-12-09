package com.example.wuyang.lsn4code;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SHAPE s=new SHAPE();
//        s.setShape(SHAPE.CIRCLE|SHAPE.RECTANGLE);
//        System.out.println(s.getShape());

        MyObjectPool pool=new MyObjectPool(2,4);
        Object o1=pool.acquire();
        Object o2=pool.acquire();
        Object o3=pool.acquire();
        Object o4=pool.acquire();
        Object o5=pool.acquire();

        Log.i("jett",o1.hashCode()+"");
        Log.i("jett",o2.hashCode()+"");
        Log.i("jett",o3.hashCode()+"");
        Log.i("jett",o4.hashCode()+"");
        Log.i("jett",o5.hashCode()+"");
//        Log.i("jett",o3.hashCode()+"");
//        Log.i("jett",o4.hashCode()+"");


    }
}
