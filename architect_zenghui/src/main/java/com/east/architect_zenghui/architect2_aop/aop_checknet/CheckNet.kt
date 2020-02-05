package com.east.architect_zenghui.architect2_aop.aop_checknet


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 标记切点 注解(这是Kotlin中的注解)
 *  @author: East
 *  @date: 2020-02-04
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION) // Target 放在哪个位置
@Retention(AnnotationRetention.RUNTIME) //RUNTIME 运行时 xUtils  CLASS 代表编译时期 ButterKnife   SOURCE 代表资源
annotation class CheckNet {

}