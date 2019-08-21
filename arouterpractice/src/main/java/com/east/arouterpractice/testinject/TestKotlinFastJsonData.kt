package com.east.arouterpractice.testinject

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  测试最新版的fastjson是否只支持  data class数据类
 *                 com.alibaba:fastjson:1.2.32  不支持 data class数据类
 *  @author: East
 *  @date: 2019-07-19
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class TestKotlinFastJsonData(var name:String,var id:Int)