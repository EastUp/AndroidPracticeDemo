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
 *  @description:  worker的输入/输出数据
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class InputOutputWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = InputOutputWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        //如果inputMerger是OverwritingInputMerger则 s = "constraintworker"
//        val s = inputData.getString("key")

        //如果inputMerger是ArrayCreatingInoputMerger则 s = "[testworker,constraintworker]"
        val s = inputData.getStringArray("key")

        Log.d(TAG,"接受到的数据为:${s!![0]}${s!![1]}")
        handler.post {
            Toast.makeText(context,"接受到的数据为:$s", Toast.LENGTH_SHORT).show()
        }



        //创建需要返回的数据
        var outputData = workDataOf("key" to "this is output data")

        //返回数据
        return Result.success(outputData)
    }
}