package com.east.arouterpractice

import android.content.Context
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.east.arouterpractice.app.App

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:   跳转失败全局降级  ,提示自己想提示的   path可以随便写
 *  @author: East
 *  @date: 2019-07-19
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Route(path = "/xx/xxx")
class DegradeServiceImpl :DegradeService {

    //失败的时候处理，注意：如果在navigation时候没有传递context，这个方法的context会是空的
    override fun onLost(context: Context?, postcard: Postcard?) {
        Toast.makeText(App.instance,"没有找到匹配路径",Toast.LENGTH_LONG).show()
    }

    override fun init(context: Context?) {
    }
}