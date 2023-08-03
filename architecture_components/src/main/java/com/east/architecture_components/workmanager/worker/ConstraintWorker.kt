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
 *  @description:  给Worker设置Constraints(限制条件)
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ConstraintWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = ConstraintWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        Log.d(TAG,"限制条件满足下运行的worker")
        handler.post {
            Toast.makeText(context,"限制条件满足下运行的worker", Toast.LENGTH_SHORT).show()
        }

        return Result.success(workDataOf("key" to "constraintWorker"))
//        return Result.failure()
    }
}