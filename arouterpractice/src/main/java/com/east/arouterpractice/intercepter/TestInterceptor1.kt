package com.east.arouterpractice.intercepter

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.east.arouterpractice.MainActivity
import java.lang.RuntimeException

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Arouter的拦截器测试
 *  @author: East
 *  @date: 2019-07-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Interceptor(priority = 7, name = "测试用的拦截器")
class TestInterceptor1 : IInterceptor {
    var mContext: Context? = null

    /**
     *  callback.onContinue(postcard);  // 处理完成，交还控制权
     *  callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程
     *  以上两种至少需要调用其中一种，否则不会继续路由
     */
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {

        if ("/arouter1/activity4" == postcard?.path) {
            Log.i("TestInterceptor拦截器","TestInterceptor1")
            // 这里的弹窗仅做举例，代码写法不具有可参考价值
            var builder = AlertDialog.Builder(MainActivity.getThis())
            builder.setTitle("温馨提醒")
            builder.setMessage("想要跳转到Test4Activity么？(触发了\"/inter/test1\"拦截器，拦截了本次跳转)")
            builder.setPositiveButton("加点儿料") { dialog, which ->
                postcard?.withString("extra", "我是拦截器中附带的参数")
                callback?.onContinue(postcard)
            }

            builder.setNegativeButton("算了") { dialog, which ->
                callback?.onInterrupt(RuntimeException("我觉得有点儿异常"))
            }

            builder.setNeutralButton("继续") { dialog, which ->
                callback?.onContinue(postcard)
            }

            Handler(Looper.getMainLooper()).post(Runnable {
                builder.create().show()
            })
        } else {
            callback?.onContinue(postcard)
        }


    }

    // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    override fun init(context: Context?) {
        mContext = context
    }

}