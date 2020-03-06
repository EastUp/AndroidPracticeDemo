package com.east.architect_zenghui.architect_27_okhttp5

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 上传进度监听回调
 *  @author: East
 *  @date: 2020/3/6
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface UploadProgressListener {
    /**
     * @param total 总共的大小
     * @param current 当前以及上传了的大小
     */
    fun onProgress(total:Long,current:Long)
}