package com.example.administrator.lsn6_demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class Main2Activity extends AppCompatActivity implements Singleton.Callback{

    MyHandler handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new MyAsyncTask().execute();
        new MyAsyncTask2(this).execute();

        handler=new MyHandler(this);
//       m.post(new Runnable() {
//           @Override
//           public void run() {
//
//           }
//       });


        Singleton.getInstance().setCallback(new Singleton.Callback() {
            @Override
            public void callback() {

            }
        });

    }

    @Override
    public void callback() {

    }

    private class MyAsyncTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            return doSomeing();
        }

        private Object doSomeing() {
            //做了很多事后
            return new Object();
        }
    }
    //这个类单独写个文件
    public class MyAsyncTask2 extends AsyncTask{

        private WeakReference<Main2Activity> main2Activity;

        public MyAsyncTask2(Main2Activity main2Activity){
            this.main2Activity=new WeakReference<>(main2Activity);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return doSomeing();
        }

        private Object doSomeing() {
            //做了很多事后
            return new Object();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

class MyHandler extends Handler{
    private WeakReference m;
    public MyHandler(Main2Activity activity){
        m=new WeakReference(activity);
    }

//    class.....
}