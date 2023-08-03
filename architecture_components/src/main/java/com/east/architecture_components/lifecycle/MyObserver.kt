package com.east.architecture_components.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-07-31
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MyObserver : LifecycleObserver {

    val TAG = MyObserver::class.java.simpleName

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG,"onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG,"onPause")
    }

}