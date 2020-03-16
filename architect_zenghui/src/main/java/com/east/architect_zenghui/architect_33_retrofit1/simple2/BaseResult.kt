package com.east.architect_zenghui.architect_33_retrofit1.simple2

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 数据返回的固定类型封装
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */
open class BaseResult (var code:String? = null,var msg:String? = null){
    /**
     *  是否成功
     */
    fun isSuccessed() = code == "200"
}