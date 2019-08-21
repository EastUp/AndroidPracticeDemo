package com.east.architecture_components.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.east.architecture_components.livedata.CustomLiveData

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  ViewModel类包含了LiveData
 *
 *                 当activity和fragment因为旋转等原因重新创建时,如果ViewModel已经创建过，则仍使用原ViewModel。
 *  @author: East
 *  @date: 2019-08-02
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MyViewModel : ViewModel() {
    val TAG = MyViewModel::class.java.simpleName
    


    var myLiveData : MutableLiveData<String> = MutableLiveData()

    var customerLiveData:CustomLiveData<String> = CustomLiveData.instace

    init {
        myLiveData.value = "heheh"
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"用户主动销毁调用 ViewModel的onCleared方法")
    }

}