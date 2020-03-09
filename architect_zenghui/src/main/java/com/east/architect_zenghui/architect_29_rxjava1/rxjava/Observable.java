package com.east.architect_zenghui.architect_29_rxjava1.rxjava;

import org.jetbrains.annotations.NotNull;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 被观察者
 *  @author: East
 *  @date: 2020/3/8
 * |---------------------------------------------------------------------------------------------------------------|
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> just(T item) {
       return onAssembly(new ObservableJust<T>(item));
    }

    private static <T> Observable<T> onAssembly(ObservableJust<T> source) {
        // 留出来了
        return source;
    }

    @Override
    public void subscribe(@NotNull Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);
}
