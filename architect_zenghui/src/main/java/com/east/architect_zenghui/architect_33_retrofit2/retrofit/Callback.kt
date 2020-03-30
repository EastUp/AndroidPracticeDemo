package com.east.architect_zenghui.architect_33_retrofit2.retrofit


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface Callback<T> {
    /**
     * Invoked for a received HTTP response.
     *
     *
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call [Response.isSuccessful] to determine if the response indicates success.
     */
    fun onResponse(call: Call<T>?, response: Response<T>?)

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    fun onFailure(call: Call<T>?, t: Throwable)
}