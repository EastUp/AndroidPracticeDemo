package com.east.architect_zenghui.architect_33_retrofit1.simple2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.east.architect_zenghui.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  Retrofit封装处理结果后的调用
 *  @author: jamin
 *  @date: 2020/3/16 2:23 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RetrofitDealResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_simple_use)

        RetrofitClient.getServiceApi().userlogin("east","12345")
            .enqueue(object :HttpCallBack<UserInfo>(){
                override fun onSuccessed(result: UserInfo) {
                    //成功
                    Toast.makeText(this@RetrofitDealResultActivity,"成功$result",Toast.LENGTH_SHORT).show()
                }

                override fun onError(code: String, msg: String) {
                    //失败
                    Toast.makeText(this@RetrofitDealResultActivity,msg,Toast.LENGTH_SHORT).show()
                }

            })
    }
}
