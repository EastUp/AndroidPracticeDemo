package com.east.architect_zenghui.architect_32_rxjava4.rxlogin.rxlogin

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/15
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class RxLoginResult(var isSucceed:Boolean=false,var msg:String? = null,var userInfoMaps:Map<String,String>?=null,
                         var platform:RxLoginPlatform=RxLoginPlatform.Platform_QQ)