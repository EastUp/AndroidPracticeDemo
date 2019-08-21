package com.east.arouterpractice.testactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.east.arouterpractice.R
import kotlinx.android.synthetic.main.activity_test3.*

@Route(path = "/arouter1/activity3")
class TestActivity3 : AppCompatActivity() {

    @Autowired
    @JvmField
    var name :String ?=null

    @Autowired
    @JvmField
    var age :Int = 0

    @Autowired(name = "boy")
    @JvmField
    var boy = true

    @Autowired
    @JvmField
    var high :Long ?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test3)

        ARouter.getInstance().inject(this)

        val params = String.format("name=%s, age=%s, boy=%s, high=%s", name, age, boy, high)

        activity3_tv_content.text = params

    }
}
