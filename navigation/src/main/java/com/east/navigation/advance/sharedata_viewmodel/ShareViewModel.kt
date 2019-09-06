package com.east.navigation.advance.sharedata_viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-09-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ShareViewModel(app : Application) : AndroidViewModel(app){

    var data : MutableLiveData<String> = MutableLiveData()




}