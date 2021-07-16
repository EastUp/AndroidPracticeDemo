package com.dn_alan.myapplication.rxjava.observable;

import com.dn_alan.myapplication.rxjava.Observable;
import com.dn_alan.myapplication.rxjava.Observer;
import com.dn_alan.myapplication.rxjava.ObserverbleSource;

//被观察者
public abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> {
    protected final ObserverbleSource<T> source;

    public AbstractObservableWithUpstream(ObserverbleSource<T> source) {
        this.source = source;
    }
}
