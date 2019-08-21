package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.east.databinding.bean.User
import com.east.databinding.databinding.ActivityTest8Binding
import com.east.databinding.databinding.ViewstubBinding

/**
 *  include和viewStub
 */
class Test8Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest8Binding =
            DataBindingUtil.setContentView<ActivityTest8Binding>(this, R.layout.activity_test8)
        val user = User("娃哈哈", "功能饮料", false)
        activityTest8Binding.user = user


        activityTest8Binding.viewStub.setOnInflateListener { stub, inflated ->

            //如果xml中没有 bind:user="@{user}"对数据进行绑定
            //那么可以在此处手动进行绑定
            val viewstubBinding = DataBindingUtil.bind<ViewstubBinding>(inflated)
            viewstubBinding?.user =user
        }

        activityTest8Binding.viewStub.viewStub!!.inflate()
    }
}
