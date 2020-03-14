package com.east.architect_zenghui.architect_31_rxjava3.rxjava

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LambdaObserver<T>constructor(var consumer:Consumer<T>) :Observer<T>{
    override fun onSubscribe() {

    }

    override fun onNext(t: T) {
        consumer.onNext(t)
    }

    override fun onError(e: Throwable) {
    }

    override fun onComplete() {
    }

}