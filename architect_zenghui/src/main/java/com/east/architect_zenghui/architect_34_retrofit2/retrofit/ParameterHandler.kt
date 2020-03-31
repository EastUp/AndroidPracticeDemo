package com.east.architect_zenghui.architect_34_retrofit2.retrofit

interface ParameterHandler {
    fun <T> apply(requestBuilder:RequestBuilder,value:T)

    // 很多策略，Query ,Part , QueryMap ,Field 等等
    class Query(var key:String) : ParameterHandler{
        // 保存 就是参数的 key = userName ,password
        override fun <T> apply(requestBuilder: RequestBuilder, value: T) {
            //添加到request中 // value -> String 要经过一个工厂
            requestBuilder.addQueryName(key,value.toString())
        }

    }


}
