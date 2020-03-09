package com.east.architect_zenghui.architect_29_rxjava1.rxjava


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R


class Rxjava1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava1)

        // 1.观察者 Observable 被观察对象
        // Observer 观察者
        // subscribe 注册订阅

        Observable.just("urlxxx")
            .subscribe(object:Observer<String>{
                override fun onComplete() {
                    Log.e("TAG", "onComplete")
                }

                override fun onSubscribe() {
                    Log.e("TAG", "onSubscribe")
                }

                override fun onNext(t: String) {
                    Log.e("TAG", "onNext--$t")
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", "onError")
                }

            })

    }
}
