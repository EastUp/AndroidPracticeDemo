package com.east.architect_zenghui.architect_34_retrofit2.retrofit.http

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Query(val value: String)