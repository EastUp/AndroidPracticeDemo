package com.east.architect_zenghui.architect2_aop.aop_singleclick

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
 *  @description:  定义切面 拦截快速点击
 *  @author: East
 *  @date: 2020-02-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Aspect
class AopClickAspect {

    /**
     *  切点
     */
    @Pointcut("execution(@com.east.architect_zenghui.architect2_aop.aop_singleclick.CheckFastClick * *(..))")
    fun onFastClick(){}


    /**
     *  定义一个 Advice 包裹切点方法
     */
    @Around("onFastClick()")
    @Throws(Throwable::class)
    fun checkFastClick(jointPoint:ProceedingJoinPoint){
        //获取方法参数
        var view:View? = null
        for (arg in jointPoint.args) {
            if(arg is View){
                view = arg
                break
            }
        }
        if(view == null) return

        val any = jointPoint.`this` //getThis() 当前切点方法所在的类
        val context = getContext(any) ?: return

        //获取注解
        val methodSignature = jointPoint.signature as MethodSignature
        if(!methodSignature.method.isAnnotationPresent(CheckFastClick::class.java))
            return
        val checkFastClick = methodSignature.method.getAnnotation(CheckFastClick::class.java)
        //判断是否快速点击
        if(FastClickUtils.checkFastClick(checkFastClick.value)){
//            Toast.makeText(context,"点击过快请慢点",Toast.LENGTH_SHORT).show()
            Log.e("TAG","点击过快请慢点")
            return
        }else{
            jointPoint.proceed()
        }
    }

    private fun getContext(any: Any): Context? {
        return when(any){
            is Activity -> any
            is Fragment -> any.context
            is View -> any.context
            else -> null
        }
    }
}