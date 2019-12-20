package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.east.databinding.databinding.ActivityTest12Binding

/**
 *  资源引用
 */
class Test12Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest12Binding =
            DataBindingUtil.setContentView<ActivityTest12Binding>(this, R.layout.activity_test12)
        activityTest12Binding.flag = true
    }
}
