package com.east.architect_zenghui.architect_27_okhttp5

import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.buffer


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 静态代理,因为RequestBody是类不是接口,所以不能使用动态代理
 *  @author: East
 *  @date: 2020/3/6
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ExMultipartBody(private var mRequestBody:MultipartBody,private var mProgressListener : UploadProgressListener?) : RequestBody() {
    private var mCurrentLength :Long = 0

    constructor(requestBody: MultipartBody):this(requestBody,null)

    override fun contentLength(): Long {
        return mRequestBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return mRequestBody.contentType()
    }

    override fun writeTo(sink: BufferedSink) {
        Log.e("TAG", "监听")
        //获取上传文件总共的长度
        val contentLength = contentLength()

        //获取当前的上传进度(也需要静态代理)
        var forwardingSink = object :ForwardingSink(sink){
            override fun write(source: Buffer, byteCount: Long) {
                mCurrentLength += byteCount
                mProgressListener?.onProgress(contentLength,mCurrentLength)
                Log.e("TAG", "$contentLength : $mCurrentLength")
                super.write(source, byteCount)
            }
        }

        //需要把sink转换成BufferedSink
        val buffer = forwardingSink.buffer()

        mRequestBody.writeTo(buffer)
        //需要刷新关流  RealConnection 连接池
        buffer.flush()

    }
}