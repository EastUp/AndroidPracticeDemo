package com.east.architect_zenghui.architect_36_retrofit_optimize.simple5;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.east.architect_zenghui.R;

public class MainActivity extends AppCompatActivity {
//    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        textView = (TextView) findViewById(R.id.textView);

        /********************访问网络开始*******************/
//        Map<String, Object> params = new HashMap<>();
//        // 特定参数
//        params.put("iid", 6152551759L);
//        params.put("aid", 7);
//        HttpUtils.with(this).cache(true).get()
//                .url("sort")
//                .param("iid", 6152551759L).param("aid", 7).request(
//                new HttpCallBack<DiscoverListResult>() {
//                    @Override
//                    public void onSuccess(DiscoverListResult result) {
//                        showListData(result);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        e.printStackTrace();
//                    }
//                });
        /********************访问网络结束*******************/

        new Thread(){
            @Override
            public void run() {
                // 直接的情况下不会出错，因为 布局的绘V制流程在 onResume 方法之后执行的,没有暂停的时候还没有调用checkThread方法
                // 暂停1s后这个时候 onResume() 一般会执行完毕，布局开始绘制，
                // 如果你再次去更新布局，那么就会调用 requestLayout() ,会去 checkThread 抛异常
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                textView.setText("111");
                Looper.prepare();
                // 子线程更新 Toast toast是加载在 WindowManager 上面的
                Toast.makeText(MainActivity.this,"111",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    private void showListData(DiscoverListResult discoverListResult) {
        Log.e("TAG",discoverListResult.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
