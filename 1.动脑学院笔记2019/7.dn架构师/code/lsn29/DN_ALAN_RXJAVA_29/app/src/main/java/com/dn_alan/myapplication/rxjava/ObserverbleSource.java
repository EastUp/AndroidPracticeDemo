package com.dn_alan.myapplication.rxjava;

public interface ObserverbleSource<T> {

    //订阅抽象方法
    void subscribe(Observer<? super T> observer);
}
