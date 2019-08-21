package com.east.architecture_components.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  在Fragment之间分享数据(通过同一个Activity获取ShareViewModel才能保证数据一致)
 *  @author: East
 *  @date: 2019-08-03
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ShareViewModel :ViewModel(){
    var data : MutableLiveData<String> = MutableLiveData()
}