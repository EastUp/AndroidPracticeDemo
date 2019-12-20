package com.east.architecture_components.workmanager.worker

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture



/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: worker中执行异步操作
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class AsynchronousWorker(context: Context,params:WorkerParameters) : ListenableWorker(context,params) {



    override fun startWork(): ListenableFuture<Result> {
//        val future = ResolvableFuture.create<Result>()
//        future.set(doWork())
//        return future

        return CallbackToFutureAdapter.getFuture<Result> { completer ->
            //做异步操作
        }

    }


    fun doWork() : Result{
        return Result.success()
    }
}