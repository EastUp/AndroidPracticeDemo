package com.east.arouterpractice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.arouter2_activity_main.*

@Route(path = "/kotlin/test")
class MainActivity : AppCompatActivity() {

    @Autowired
    @JvmField
    var name: String? = null
    @Autowired
    @JvmField
    var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter2_activity_main)

        tv_content.text = "name：$name-----age：$age"
//        tv_content.text = "name："
    }
}
