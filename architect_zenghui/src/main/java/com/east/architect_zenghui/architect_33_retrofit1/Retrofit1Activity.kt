package com.east.architect_zenghui.architect_33_retrofit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_33_retrofit1.simple1.RetrofitSimpleUseActivity
import com.east.architect_zenghui.architect_33_retrofit1.simple2.RetrofitDealResultActivity
import kotlinx.android.synthetic.main.activity_retrofit1.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  Retrofit源码设计模式分析及网络接口回调封装
 *  @author: jamin
 *  @date: 2020/3/16 9:35 AM
 * |---------------------------------------------------------------------------------------------------------------| 
 */
class Retrofit1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit1)
    }

    fun onClick(v: View){
        when(v){
            //基本使用
            retrofit_simple_use -> startActivity(Intent(this,RetrofitSimpleUseActivity::class.java))
            //Retrofit封装处理结果后的调用
            retrofit_deal_result -> startActivity(Intent(this,RetrofitDealResultActivity::class.java))
        }
    }
}
