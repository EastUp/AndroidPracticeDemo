package com.east.architecture_components.livedata

import androidx.lifecycle.MutableLiveData

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  单例模式下LiveData实现数据共享
 *  @author: East
 *  @date: 2019-08-02
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CustomLiveData<T> private constructor(): MutableLiveData<T>() {



    //kotlin下的单例模式
    companion object{
        val instace : CustomLiveData<String> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            CustomLiveData<String>()
        }
    }
}