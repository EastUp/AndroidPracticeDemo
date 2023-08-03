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
 *  @description:  Work简单的示例
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class InputDataWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = InputDataWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        //这里做工作的具体内容
        val data = inputData.getString("workerData")
        Log.d(TAG,"获取到执行任务时需要的参数:$data")

        handler.post {
            Toast.makeText(context,"测试work是在子线程运行的", Toast.LENGTH_SHORT).show()
        }

        return Result.success()
//        return Result.failure()
//
//        /**
//         * 会有一个默认的后退延时和策略,可以通过WorkRequest.setBackOffCriteria来设置延迟和策略
//         */
//
//        return Result.retry()
    }
}