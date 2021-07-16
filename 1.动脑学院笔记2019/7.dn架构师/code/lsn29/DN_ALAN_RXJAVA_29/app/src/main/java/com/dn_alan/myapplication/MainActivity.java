package com.dn_alan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dn_alan.myapplication.rxjava.Disposeble;
import com.dn_alan.myapplication.rxjava.Function;
import com.dn_alan.myapplication.rxjava.Observable;
import com.dn_alan.myapplication.rxjava.ObservableEmitter;
import com.dn_alan.myapplication.rxjava.ObservableOnSubscribe;
import com.dn_alan.myapplication.rxjava.Observer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "subscribe 事件发射");
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {

            @Override
            public String apply(Integer integer) {
                return integer + "alan";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposeble d) {
                Log.d(TAG, "onSubscribe 成功");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onSubscribe===" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposeble d) {
//                Log.d(TAG, "onSubscribe 成功");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onSubscribe===" + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete");
//            }
//        };
//        observable.subscribe(observer);


    }
}
