package com.east.architecture_components.workmanager.worker

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  Work简单的示例
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TestWorker(var context: Context,workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = TestWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        Log.d(TAG,"测试work是在子线程运行的")
        handler.post {
            Toast.makeText(context,"测试work是在子线程运行的", Toast.LENGTH_SHORT).show()
        }

        return Result.success(workDataOf("key" to "testworker"))
    }
}