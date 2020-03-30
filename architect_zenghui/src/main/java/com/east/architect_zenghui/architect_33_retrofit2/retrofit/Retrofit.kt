package com.east.architect_zenghui.architect_33_retrofit2.retrofit

import okhttp3.OkHttpClient
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:手写Retrofit中的核心内容
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Retrofit(builder:Builder) {
    var baseUrl = builder.baseUrl
    var okHttpClient = builder.okhttpClient
    val serviceMethodMapCache = ConcurrentHashMap<Method,ServiceMethod>()

    fun <T> create(clazz:Class<T>): T {
        //检验，是不是一个借口，不能让它继承子接口


        //重点
        return Proxy.newProxyInstance(
            clazz.classLoader,
            arrayOf(clazz),
            object :InvocationHandler{
                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>): Any {
                    //每一个方法都会执行到这,
                    //判断是否是Object的方法 ?
                    if(method.declaringClass == Any::class.java){
                        return method.invoke(this, args)
                    }

                    // 解析参数注解
                    var serviceMethod = loadServiceMethod(method)
                    //2.封装 OkHttpCall
//                    val genericReturnType = method.genericReturnType
//                    val parameterizedType = genericReturnType as ParameterizedType
//                    val clazz1 = parameterizedType.actualTypeArguments[0]
                    var okHttpCall = OkHttpCall(serviceMethod,args)
                    return okHttpCall
                }
            }
        ) as T
    }

    /**
     *  解析并缓存method
     */
    fun loadServiceMethod(method: Method) : ServiceMethod{
        //享元设计模式
        var serviceMethod = serviceMethodMapCache[method]
        if(serviceMethod == null){
            serviceMethod = ServiceMethod.Builder(this,method).builder()
            serviceMethodMapCache[method] = serviceMethod
        }
        return serviceMethod
    }


    class Builder{
        var baseUrl:String = ""
        var okhttpClient : OkHttpClient ? =null

        fun baseUrl(baseUrl:String):Builder{
            this.baseUrl = baseUrl
            return this
        }

        fun client(okhttpClient : OkHttpClient):Builder{
            this.okhttpClient = okhttpClient;
            return this;
        }

        fun build():Retrofit{
            if(okhttpClient == null)
                okhttpClient = OkHttpClient()
            return Retrofit(this)
        }
    }
}