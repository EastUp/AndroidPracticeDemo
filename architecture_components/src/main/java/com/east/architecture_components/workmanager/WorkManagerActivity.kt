package com.east.architecture_components.workmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.east.architecture_components.R
import com.east.architecture_components.workmanager.worker.*
import kotlinx.android.synthetic.main.activity_work_manager.*
import java.util.concurrent.TimeUnit

/**
 *  通过WorkManager API，可以轻松安排即使应用程序退出或设备重新启动也可以运行的可延迟的异步任务。
 */
class WorkManagerActivity : AppCompatActivity() {

    val TAG = WorkManagerActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
    }

    fun onClick(v: View){
        when(v){
            btn_simple_task -> {
                var testWorkRequest =  PeriodicWorkRequestBuilder<TestWorker>(3,TimeUnit.SECONDS).build()
//                var testWorkRequest = OneTimeWorkRequestBuilder<TestWorker>().build()
//                var testWorkRequest = OneTimeWorkRequest.Builder(TestWorker::class.java)
                WorkManager.getInstance(this).enqueue(testWorkRequest)
            }


            /**
             * 指定网络状态执行任务
             * NetworkType.NOT_REQUIRED：对网络没有要求
             * NetworkType.CONNECTED：网络连接的时候执行
             * NetworkType.UNMETERED：不计费的网络比如WIFI下执行
             * NetworkType.NOT_ROAMING：非漫游网络状态
             * NetworkType.METERED：计费网络比如3G，4G下执行。
             */
            //约束条件
            btn_constraints -> {
                var constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)////不在电量不足时执行
                    .setRequiresCharging(true)////在充电时执行
//                    .setRequiresDeviceIdle(true)////在待机状态执行
                    .setRequiresStorageNotLow(true)//不在存储容量不足时执行
//                    .setTriggerContentMaxDelay(Duration.ZERO)
//                    .setTriggerContentUpdateDelay(Duration.ZERO)
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()

                var request = OneTimeWorkRequestBuilder<ConstraintWorker>()
                    .setConstraints(constraints)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
            }

            //设置延时
            btn_initial_delay -> {
                var request = OneTimeWorkRequest.Builder(InitialDelayWorker::class.java)
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
            }

            //重试和补偿策略
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
             *
             * BackoffPolicy.LINEAR 设置BackoffPolicy延时为10s则退避延迟时间规律为: 10 20 30 40 50 即  (10 * 尝试次数)
             * BackoffPolicy.Exponential 设置BackoffPolic延时为10s后退延迟时间规律为
             *                   Math.scalb(10, 尝试次数 - 1)    即    10 × 2^(尝试次数-1) 
             */
            btn_retry_backoff -> {
                var request = OneTimeWorkRequestBuilder<RetryWorker>()
//                    .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)//线性方式增加退避时间
                    .setBackoffCriteria(BackoffPolicy.EXPONENTIAL,10,TimeUnit.SECONDS) //指数方式成倍增加退避时间
                    .build()
                WorkManager.getInstance(this).enqueue(request)
            }


            btn_inputout ->{
                var data = workDataOf("key" to "this is input data")
//                var data = Data.Builder().putString("key","this is input data").build()
                var request = OneTimeWorkRequestBuilder<InputOutputWorker>()
                    .setInputData(data)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
            }

            btn_tag ->{
                var request = OneTimeWorkRequestBuilder<TestWorker>()
                    .addTag("clean up")
                    .build()

                //关闭所有tag为clean up的任务
//                WorkManager.getInstance(this).cancelAllWorkByTag("clean up")

                WorkManager.getInstance(this).enqueue(request)
            }

            btn_observing_word -> {
                var request = OneTimeWorkRequestBuilder<InputOutputWorker>()
                    .addTag("clean up")
                    .build()


                WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this, Observer {
                    if(it != null && it.state == WorkInfo.State.SUCCEEDED){
                        val value = it.outputData.getString("key")
                        Log.d(TAG,"work 完成,接收到数据为:$value")
                    }
                })

                //val listenableFuture = WorkManager.getInstance(this).getWorkInfosByTag("clean up")
                //val workInfo = listenableFuture.get()

                //WorkManager.getInstance(this).getWorkInfosForUniqueWork("hehe")



                WorkManager.getInstance(this).enqueue(request)
                //WorkManager.getInstance(this).enqueueUniqueWork("hehe",ExistingWorkPolicy.REPLACE,request)
            }

            btn_work_chain -> {
                var request = OneTimeWorkRequestBuilder<TestWorker>().build()
                var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>().build()
                var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>().build()

                WorkManager.getInstance(this)
                        //workRequest并行运行
                    .beginWith(arrayListOf(request,requestConstraint))
                        //在之前的workRequst之后运行
                    .then(requestInputOutputWorker)
                        //最后别忘了enqueue
                    .enqueue()
            }

            btn_inputMergers -> {

                var request = OneTimeWorkRequestBuilder<TestWorker>()
                    .build()
                var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>()
                    .build()
                var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>()
                    .setInputMerger(ArrayCreatingInputMerger::class.java)
                    .build()


                WorkManager.getInstance(this)
                    //workRequest并行运行
                    .beginWith(arrayListOf(request,requestConstraint))
                    //在之前的workRequst之后运行
                    .then(requestInputOutputWorker)
                    //最后别忘了enqueue
                    .enqueue()
            }

            btn_periodic -> {
                var constraints = Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()

                //过了间隔时间后,也会观察是否满足充电的约束条件,是的话才会执行(最小间隔时间必须大于等于15分钟)
                var request = PeriodicWorkRequestBuilder<TestWorker>(5,TimeUnit.SECONDS)
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(this).enqueue(request)
            }

            btn_unique_work -> {
                var request = PeriodicWorkRequestBuilder<TestWorker>(15,TimeUnit.MINUTES).build()

                WorkManager.getInstance(this).enqueueUniquePeriodicWork("test",ExistingPeriodicWorkPolicy.KEEP,request)
            }

            btn_unique_work_chain -> {
                var request = OneTimeWorkRequestBuilder<TestWorker>()
                    .build()
                var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>()
                    .build()
                var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>()
                    .setInputMerger(ArrayCreatingInputMerger::class.java)
                    .build()

                WorkManager.getInstance(this)
                    .beginUniqueWork("test",ExistingWorkPolicy.REPLACE,arrayListOf(request,requestConstraint))
                    .then(requestInputOutputWorker)
                    .enqueue()
            }

            btn_CoroutineWorker -> {
                var request = OneTimeWorkRequestBuilder<KotlinWorker>().build()
                WorkManager.getInstance(this).enqueue(request)
            }



            close_all_workder -> {
                WorkManager.getInstance(this).cancelAllWork()
            }

        }
    }
}
