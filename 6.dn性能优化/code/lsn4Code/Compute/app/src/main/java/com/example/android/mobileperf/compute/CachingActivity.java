package com.example.android.mobileperf.compute;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class CachingActivity extends Activity {
    public static final String LOG_TAG = "Jett";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = (Button) findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("计算斐波那契数列");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, String.valueOf(computeFibonacci(40)));
            }
        });
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

//    public int computeFibonacci(int positionInFibSequence) {
//        //0 1 1 2 3 5 8
//        if (positionInFibSequence <= 2) {
//            return 1;
//        } else {
//            return computeFibonacci(positionInFibSequence - 1)
//                    + computeFibonacci(positionInFibSequence - 2);
//        }
//    }
    //优化后的斐波那契数列的非递归算法 caching缓存+批处理思想
    public int computeFibonacci(int positionInFibSequence) {
        int prev = 0;
        int current = 1;
        int newValue;
        for (int i=1; i<positionInFibSequence; i++) {
            newValue = current + prev;
            prev = current;
            current = newValue;
        }
        return current;
    }
}
