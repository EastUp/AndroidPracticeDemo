package com.example.android.mobileperf.compute;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Random;


public class MemoryChurnActivity extends Activity {
    public static final String LOG_TAG = "Jett";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = (Button) findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("走一个");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPrettySureSortingIsFree();
            }
        });
//        WebView webView = (WebView) findViewById(R.id.webview);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     *　排序后打印二维数组，一行行打印
     */
//    public void imPrettySureSortingIsFree() {
//        int dimension = 300;
//        int[][] lotsOfInts = new int[dimension][dimension];
//        Random randomGenerator = new Random();
//        for (int i = 0; i < lotsOfInts.length; i++) {
//            for (int j = 0; j < lotsOfInts[i].length; j++) {
//                lotsOfInts[i][j] = randomGenerator.nextInt();
//            }
//        }
//
//        for (int i = 0; i < lotsOfInts.length; i++) {
//            String rowAsStr = "";
//            //排序
//            int[] sorted = getSorted(lotsOfInts[i]);
//            //拼接打印
//            for (int j = 0; j < lotsOfInts[i].length; j++) {
//                rowAsStr += sorted[j];
//                if (j < (lotsOfInts[i].length - 1)) {
//                    rowAsStr += ", ";
//                }
//            }
//            Log.i("ricky", "Row " + i + ": " + rowAsStr);
//        }
//    }
        /**
         *　打印二维数组，一行行打印
         */
        public void imPrettySureSortingIsFree() {
            int dimension = 300;
            int[][] lotsOfInts = new int[dimension][dimension];
            Random randomGenerator = new Random();
            for(int i = 0; i < lotsOfInts.length; i++) {
                for (int j = 0; j < lotsOfInts[i].length; j++) {
                    lotsOfInts[i][j] = randomGenerator.nextInt();
                }
            }

            // 使用StringBuilder完成输出，我们只需要创建一个字符串即可，不需要浪费过多的内存
            StringBuilder sb = new StringBuilder();
            String rowAsStr = "";
            for(int i = 0; i < lotsOfInts.length; i++) {
                // 清除上一行
                sb.delete(0, rowAsStr.length());
                //排序
                int[] sorted = getSorted(lotsOfInts[i]);
                //拼接打印
                for (int j = 0; j < lotsOfInts[i].length; j++) {
                    sb.append(sorted[j]);
                    if(j < (lotsOfInts[i].length - 1)){
                        sb.append(", ");
                    }
                }
                rowAsStr = sb.toString();
                Log.i("jason", "Row " + i + ": " + rowAsStr);
            }
        }


        public int[] getSorted(int[] input){
            int[] clone = input.clone();
            Arrays.sort(clone);
            return clone;
        }
}
