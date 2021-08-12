package com.dn_alan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "subscribe 事件发射==" + Thread.currentThread().getName());
                        emitter.onNext(1);
                        emitter.onComplete();
                    }
                }).map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) {
                        Log.d(TAG, "apply===" + Thread.currentThread().getName());  //io 子
                        return integer + "alan";
                    }
                }).subscribeOn().observeOn()
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposeble d) {
                                Log.d(TAG, "onSubscribe 成功==" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG, "onNext===" + s + "==" + Thread.currentThread().getName());
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
            }
        }).start();

//        new test().test();
    }
}
