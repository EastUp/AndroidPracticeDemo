package com.east.arouterpractice.testactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.east.arouterpractice.R
import kotlinx.android.synthetic.main.activity_test4.*

@Route(path = "/arouter1/activity4")
class TestActivity4 : AppCompatActivity() {

    @Autowired
    @JvmField
    var extra :String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test4)

        tv_content.text = "extra:$extra"
    }
}
