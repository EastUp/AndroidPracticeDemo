package com.east.architect_zenghui.architect_31_rxjava3.rxjava;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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

    private static <T> Observable<T> onAssembly(Observable<T> source) {
        // 留出来了
        return source;
    }

    @Override
    public void subscribe(@NotNull Observer<? super T> observer) {
        subscribeActual(observer);
    }

    public void subscribe(Consumer<T> onNext){
        subscribe(onNext,null,null);
    }

    private void subscribe(Consumer<T> onNext, Consumer<T> error, Consumer<T> complete) {
        subscribe(new LambdaObserver<T>(onNext));
    }

    protected abstract void subscribeActual(Observer<? super T> observer);

    public <R> Observable<R> map(Function<T,R> function){
        return onAssembly(new ObservableMap(this,function));
    }

    public Observable<T> subscribeOn(@Nullable Schedulers schedulers) {
        return onAssembly(new ObservableSchedulers(this,schedulers));
    }

    @NotNull
    public Observable<T> observerOn(@Nullable Schedulers schedulers) {
        return onAssembly(new ObserverOnObservable<T>(this,schedulers));
    }
}
