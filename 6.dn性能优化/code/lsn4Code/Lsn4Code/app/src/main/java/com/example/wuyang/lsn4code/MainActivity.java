package com.example.wuyang.lsn4code;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SHAPE s=new SHAPE();
//        s.setShape(SHAPE.CIRCLE|SHAPE.RECTANGLE);
//        System.out.println(s.getShape());

        MyObjectPool pool=new MyObjectPool(2,4);
        Object o1= null;
        Object o2= null;
        Object o3= null;
        Object o4= null;
        Object o5= null;
        try {
            o1 = pool.acquire();
            o2 = pool.acquire();
            o3 = pool.acquire();
            o4 = pool.acquire();
            o5 = pool.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("jett",o1.hashCode()+"");
        Log.i("jett",o2.hashCode()+"");
        Log.i("jett",o3.hashCode()+"");
        Log.i("jett",o4.hashCode()+"");
        Log.i("jett",o5 == null ?" " : o5.hashCode()+"");
//        Log.i("jett",o3.hashCode()+"");
//        Log.i("jett",o4.hashCode()+"");


    }
}
