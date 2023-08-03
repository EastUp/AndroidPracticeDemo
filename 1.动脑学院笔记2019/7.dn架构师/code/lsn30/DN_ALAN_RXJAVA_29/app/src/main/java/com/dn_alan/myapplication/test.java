package com.dn_alan.myapplication;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class test {
    private static final String TAG = "MainActivity";
    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<Integer, String>() {

                            @Override
                            public String apply(Integer integer) throws Exception {
                                Log.d(TAG, "apply===" + Thread.currentThread().getName());  //io 子
                                return integer + "alan";
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe 成功==" + Thread.currentThread().getName());// new Thred
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG, "onNext===" + s + "==" + Thread.currentThread().getName());  //主
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).start();

    }
}
