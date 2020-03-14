package com.east.architect_zenghui.architect_31_rxjava3.rxjava;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description:
 * @author: jamin
 * @date: 2020/3/14
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ObservableMap<T,R> extends Observable<R> {

    final Observable<T> source;
    final Function<T,R> function;

    public ObservableMap(Observable<T> source, Function<T, R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        source.subscribeActual(new MapObserver<T>(observer,function));
    }

    public class MapObserver<T> implements Observer<T> {

        final Observer<? super R> observer;
        final Function<T,R> function;

        public MapObserver(Observer<? super R> observer, Function<T, R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onSubscribe() {
            observer.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            // item 是 String  xxxUrl
            // 要去转换 String -> Bitmap
            // 4.第四步 function.apply
            try {
                R applyR = function.apply(t);
                // 6. 第六步，调用 onNext
                // 把 Bitmap 传出去
                observer.onNext(applyR);
            } catch (Exception e) {
                e.printStackTrace();
                observer.onError(e);
            }
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
