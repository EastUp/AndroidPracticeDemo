package com.east.architect_zenghui.architect_29_rxjava1.rxjava;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020/3/8
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ObservableJust<T> extends Observable<T> {

    private final T value;

    public ObservableJust(T item) {
        this.value = item;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ScalarDisposable sd = new ScalarDisposable(observer,value);
        observer.onSubscribe();
        sd.run();
    }

    private class ScalarDisposable<T>{

        private Observer observer;
        private T value;

        public ScalarDisposable(Observer<? super T> observer, T value) {
            this.observer = observer;
            this.value = value;
        }

        public void run(){
            try {
                observer.onNext(value);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }
    }
}
