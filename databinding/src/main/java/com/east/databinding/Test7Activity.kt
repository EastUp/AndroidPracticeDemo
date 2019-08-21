package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.east.databinding.bean.User
import com.east.databinding.databinding.ActivityTest7Binding
import kotlinx.android.synthetic.main.activity_test7.*

/**
 *  运算符
 */
class Test7Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest7Binding =
            DataBindingUtil.setContentView<ActivityTest7Binding>(this, R.layout.activity_test7)
        activityTest7Binding.user = User("名字",null,true)

    }
}
