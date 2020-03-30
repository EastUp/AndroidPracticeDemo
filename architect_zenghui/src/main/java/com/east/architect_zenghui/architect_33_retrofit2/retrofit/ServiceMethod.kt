package com.east.architect_zenghui.architect_33_retrofit2.retrofit

import com.east.architect_zenghui.architect_33_retrofit2.retrofit.http.GET
import com.east.architect_zenghui.architect_33_retrofit2.retrofit.http.POST
import com.east.architect_zenghui.architect_33_retrofit2.retrofit.http.Query
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.ResponseBody
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30 16:22
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ServiceMethod(builder: Builder) {
    val retrofit = builder.retrofit
    val method = builder.method
    val httpMethod = builder.httpMethod
    val relativeUrl = builder.relativeUrl
    val parameterHandlers = builder.parameterHandlers


    fun createNewCall(args: Array<out Any>): Call {
        // 还需要一个对象，专门用来添加参数的
        var requestBuilder =
            RequestBuilder(retrofit.baseUrl, relativeUrl, httpMethod, parameterHandlers, args)
        return retrofit.okHttpClient!!.newCall(requestBuilder.build())
    }

    fun <T> parseBody(body: ResponseBody?): T {
        // 获取解析类型 T 获取方法返回值的类型
        val genericReturnType = method.genericReturnType
        val parameterizedType = genericReturnType as ParameterizedType
        val clazz = parameterizedType.actualTypeArguments[0] as Class<T>
        // 解析工厂去转换
        return Gson().fromJson(body!!.charStream(),clazz)
    }

    class Builder(retrofit: Retrofit, method: Method) {
        val retrofit: Retrofit
        val method: Method
        val methodAnnotations: Array<Annotation>
        var httpMethod: String? = null
        var relativeUrl: String? = null
        val parameterAnnotations: Array<Array<Annotation>>
        val parameterHandlers: Array<ParameterHandler?>

        init {
            this.retrofit = retrofit
            this.method = method
            methodAnnotations = method.annotations
            //二位数组
            parameterAnnotations = method.parameterAnnotations
            parameterHandlers = arrayOfNulls(parameterAnnotations.size)
        }

        fun builder(): ServiceMethod {
            // 解析，OkHttp 请求的时候 ，url = baseUrl + relativeUrl，method
            methodAnnotations.forEach {
                // 解析 POST  GET
                parseAnnotationMethod(it)
            }

            //解析参数注解
            val count = parameterAnnotations.size
            for ((index, value) in parameterAnnotations.withIndex()) {
                val parameter = value[0]
                // Query 等等
                // 会涉及到一个模板和策略两种设计模式
                if (parameter is Query) {
                    // 一个一个封装成 ParameterHandler ，不同的参数注解选择不同的策略
                    parameterHandlers[index] = ParameterHandler.Query(parameter.value)
                }
            }
            return ServiceMethod(this)

        }

        private fun parseAnnotationMethod(methodAnnotation: Annotation) {
            // value ,请求方法
            when (methodAnnotation) {
                is GET -> parseMethodAndPath("GET", methodAnnotation.value)
                is POST -> parseMethodAndPath("POST", methodAnnotation.value)
                // ...
            }
        }

        private fun parseMethodAndPath(method: String, value: String) {
            this.httpMethod = method
            this.relativeUrl = value
        }

    }
}
