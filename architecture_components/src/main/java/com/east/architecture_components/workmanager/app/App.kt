package com.east.architecture_components.workmanager.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import java.util.concurrent.Executors

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 自定义 WorkManager的初始化,按需WorkManager初始化
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class App : Application(),Configuration.Provider {
    override fun onCreate() {
        super.onCreate()

        //提供自定义的配置
        var config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setExecutor(Executors.newSingleThreadExecutor())
            .build()

        //初始化WorkManager
        WorkManager.initialize(this,config)

    }


    //使用按需初始化workManager时 需要实现接口
    override fun getWorkManagerConfiguration(): Configuration {
        //提供自定义的配置
        var config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setExecutor(Executors.newSingleThreadExecutor())
            .build()

        return config
    }
}