package com.east.architect_zenghui.architect2_aop.aop_checknet

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 定义切面
 *  @author: East
 *  @date: 2020-02-04
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Aspect
class SectionAspect {

    /**
     * 定义切点,标记切点为所有被 @CheckNet 注解修饰的方法
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.east.architect_zenghui.architect2_aop.aop_checknet.CheckNet * *(..))")
    fun onCheckNet() {}

    /**
     *   Advice 处理切面
     */
    @Around("onCheckNet()")
    @Throws(Throwable::class)
    fun handleCheckNet(joinPoint: ProceedingJoinPoint): Any? {
        Log.e("TAG", "checkNet")
        // 做埋点  日志上传  权限检测（我写的，RxPermission , easyPermission） 网络检测
        // 网络检测
        // 1.获取 CheckNet 注解  NDK  图片压缩  C++ 调用Java 方法
        var signature = joinPoint.signature as MethodSignature
        //只处理有 CheckNet 注解修饰的方法
        if (signature.method.isAnnotationPresent(CheckNet::class.java)) {
            signature.method.getAnnotation(CheckNet::class.java) ?: return null
            // 2.判断有没有网络  怎么样获取 context?
            val any = joinPoint.`this` // View Activity Fragment ； getThis() 当前切点方法所在的类
            var context = getContext(any)
            if(context!=null){
                if(!CheckNetUtil.isNetworkAvailable(context)){
                    // 3.没有网络不要往下执行
                    Toast.makeText(
                        context,
                        "请检查您的网络",
                        Toast.LENGTH_LONG
                    ).show()
                    return null
                }
            }
        }
        return joinPoint.proceed()
    }

    private fun getContext(any: Any): Context? {
        return when (any) {
            is Activity -> any
            is Fragment -> any.context
            is View -> any.context
            else -> null
        }
    }


}