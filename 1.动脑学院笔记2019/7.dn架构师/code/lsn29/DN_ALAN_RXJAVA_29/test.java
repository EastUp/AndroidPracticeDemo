package com.dn_alan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Observable ---被观察者
         * create  ----操作符
         * ObservableEmitter ---- 发射器向观察者发送事件
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(1 + "alan");
                e.onComplete();
            }
        });

        /**
         * Observer --- 观察者
         */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete()");
            }
        };
        observable.subscribe(observer);

        Observable.just(1)
                .map(new Function<Integer, String >() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "I'm " + integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer < String > () {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "===================onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "===================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
