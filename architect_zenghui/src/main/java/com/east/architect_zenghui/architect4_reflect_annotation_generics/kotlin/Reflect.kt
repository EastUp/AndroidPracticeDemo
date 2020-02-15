package com.east.architect_zenghui.architect4_reflect_annotation_generics.kotlin

import kotlin.reflect.full.memberProperties

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Kotlin反射
 *  @author: East
 *  @date: 2020-02-09
 * |---------------------------------------------------------------------------------------------------------------|
 */

inline fun <reified T : Any> T.description()
        = this.javaClass.kotlin.memberProperties.map {
             "${it.name}:${it.get(this@description)}"
        }.joinToString(separator = ";")