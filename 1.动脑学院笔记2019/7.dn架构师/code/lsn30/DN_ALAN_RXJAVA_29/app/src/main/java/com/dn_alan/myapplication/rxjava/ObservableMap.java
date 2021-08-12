package com.dn_alan.myapplication.rxjava;

import com.dn_alan.myapplication.rxjava.observable.AbstractObservableWithUpstream;
import com.dn_alan.myapplication.rxjava.observable.BasicFuseableObserver;

public class ObservableMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends U> function;

    public ObservableMap(ObserverbleSource<T> source, Function<? super T, ? extends U> function) {
        super(source);
        this.function = function;
    }

    @Override
    protected void subscribeActual(Observer<? super U> observer) {
        source.subscribe(new MapObserver<>(observer, function));
    }

    //观察者
    static final  class MapObserver<T, U> extends BasicFuseableObserver<T, U>{
        final Function<? super T, ? extends U> mapper;

        public MapObserver(Observer<? super U> actual, Function<? super T, ? extends U> mapper) {
            super(actual);
            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            U apply = mapper.apply(t);

            actual.onNext(apply);
        }
    }

}
