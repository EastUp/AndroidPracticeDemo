package com.east.architect_zenghui.architect15_designmode8_observe.simple4;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 被观察者
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class Observable<M,T extends Observer<M>> {
    private List<Observer<M>> mObservers = new ArrayList<>();

    public void register(Observer<M> observer){
        mObservers.add(observer);
    }

    public void unregister(Observer<M> observer){
        mObservers.remove(observer);
    }

    public void notifyObservers(M m){
        for (Observer<M> mObserver : mObservers) {
            mObserver.update(this,m);
        }
    }
}
