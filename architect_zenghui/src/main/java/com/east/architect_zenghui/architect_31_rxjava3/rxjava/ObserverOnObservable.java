package com.east.architect_zenghui.architect_31_rxjava3.rxjava;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/14
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ObserverOnObservable<T> extends Observable<T> {
    final Observable<T> source;
    final Schedulers schedulers;

    public ObserverOnObservable(Observable<T> source, Schedulers mainThread) {
        this.source = source;
        this.schedulers = mainThread;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        source.subscribe(new ObserverOnObserver<>(observer));
    }


    private class ObserverOnObserver<T> implements Observer<T>,Runnable{

        final Observer<? super T> observer;

        private T value;
        public ObserverOnObserver(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            value = t;
            schedulers.scheduleDirect(this);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public void run() {
            // 主线程 或者 其他
            observer.onNext(value);
        }
    }
}
