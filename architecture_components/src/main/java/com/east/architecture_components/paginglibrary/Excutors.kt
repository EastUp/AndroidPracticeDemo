package com.east.architecture_components.paginglibrary

import java.util.concurrent.Executors

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-10
 * |---------------------------------------------------------------------------------------------------------------|
 */

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 *  在专用的后台线程上允许Block的实体方法,用于io/database 请求
 */
fun ioThread(f : () -> Unit){
    IO_EXECUTOR.execute(f)
}