package com.east.architect_zenghui.architect_33_retrofit1.simple1

import com.east.architect_zenghui.architect_34_retrofit2.simple.BaseResult

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 对接口返回数据的第二层封装
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */

// 成功是一个对象正常，不成功是一个 String （出错）,所以弄成一个Object
data class UserLoginResult(var data:Any): BaseResult()