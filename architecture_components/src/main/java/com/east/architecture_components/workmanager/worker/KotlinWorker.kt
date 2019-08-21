package com.east.architecture_components.workmanager.worker

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  适合kotlin用户的协同Worker  CoroutineWorker
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class KotlinWorker(var context:Context, params:WorkerParameters) : CoroutineWorker(context,params) {


    //已经过时请在doWork方法中使用withContext来指定任务执行时运行的线程
//    override val coroutineContext: CoroutineDispatcher
//        get() = Dispatchers.IO

    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main){
            Toast.makeText(context,"证明CoroutineWorker可通过CoroutineContext来定义执行线程",Toast.LENGTH_SHORT).show()
        }

        return Result.success()
    }
}