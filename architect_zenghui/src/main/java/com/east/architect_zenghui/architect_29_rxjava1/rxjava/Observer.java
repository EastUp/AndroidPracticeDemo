package com.east.architect_zenghui.architect_29_rxjava1.rxjava;

import io.reactivex.annotations.NonNull;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 观察者
 *  @author: East
 *  @date: 2020/3/8
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface Observer<T> {
    void onSubscribe();

    void onNext(@NonNull T t);

    void onError(@NonNull Throwable e);

    void onComplete();
}
