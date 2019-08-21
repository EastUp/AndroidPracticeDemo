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
 *  @description:  worker重试,以及设置退避时间和策略
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RetryWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = RetryWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {


        Log.d(TAG,"worker重试")
        handler.post {
            Toast.makeText(context,"worker重试", Toast.LENGTH_SHORT).show()
        }

        /**
         * 会有一个默认的后退延时和策略,可以通过--WorkRequest.setBackOffCriteria---来设置延迟和策略
         *
         * 默认
         *     退避策略BackoffPolicy:BackoffPolicy.Exponential  指数形式
         *     时间为:30s    public static final long DEFAULT_BACKOFF_DELAY_MILLIS = 30000L;
         *
         * 注意:
         *     最小的退避延迟是10s    public static final long MIN_BACKOFF_MILLIS = 10 * 1000; // 10 seconds.
         *     最大的退避延迟是5小时    public static final long MAX_BACKOFF_MILLIS = 5 * 60 * 60 * 1000; // 5 hours.
         */

        return Result.retry()
    }
}