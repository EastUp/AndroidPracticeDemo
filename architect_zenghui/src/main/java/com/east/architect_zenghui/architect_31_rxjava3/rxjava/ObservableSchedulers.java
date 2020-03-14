package com.east.architect_zenghui.architect_31_rxjava3.rxjava;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/14
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class ObservableSchedulers<T> extends Observable<T> {

    final Observable source;
    final Schedulers schedulers;

    public ObservableSchedulers(Observable source, Schedulers schedulers) {
        this.source = source;
        this.schedulers = schedulers;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        schedulers.scheduleDirect(new SchedulerTask(observer));
    }

    private class SchedulerTask implements Runnable{

        final Observer<? super T> observer;

        public SchedulerTask(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void run() {
            // 线程池最终回来执行 Runnable -> 这行代码，会执行上游的 subscribe()
            // 而这个run方法在子线程中
            source.subscribe(observer);
        }
    }
}
