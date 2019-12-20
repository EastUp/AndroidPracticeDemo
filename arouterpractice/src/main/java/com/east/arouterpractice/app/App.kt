package com.east.arouterpractice.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.east.arouterpractice.BuildConfig

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-07-11
 * |---------------------------------------------------------------------------------------------------------------|
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this

        if(BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }

        ARouter.init(this)
    }

    companion object{
        var instance :App?= null
    }
}