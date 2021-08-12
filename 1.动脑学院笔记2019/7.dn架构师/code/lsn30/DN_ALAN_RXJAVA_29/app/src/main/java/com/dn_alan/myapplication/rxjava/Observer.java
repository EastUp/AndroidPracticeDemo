package com.dn_alan.myapplication.rxjava;

public interface Observer<T> {
    void onSubscribe(Disposeble d);

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();
}
