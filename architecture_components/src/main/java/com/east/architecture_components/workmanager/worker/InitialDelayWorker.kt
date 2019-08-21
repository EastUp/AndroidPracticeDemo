package com.east.architecture_components.workmanager.worker

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  给worker设置延迟执行
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class InitialDelayWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = InitialDelayWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {


        Log.d(TAG,"Inital Delay worker在延迟执行")
        handler.post {
            Toast.makeText(context,"worker在延迟执行", Toast.LENGTH_SHORT).show()
        }

        return Result.success()
//        return Result.failure()
    }
}