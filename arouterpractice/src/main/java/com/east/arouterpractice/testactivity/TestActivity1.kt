package com.east.arouterpractice.testactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.east.arouterpractice.R
import com.east.arouterpractice.testinject.TestKotlinFastJsonData
import com.east.arouterpractice.testinject.TestObj
import com.east.arouterpractice.testinject.TestParcelable
import com.east.arouterpractice.testinject.TestSerializable
import kotlinx.android.synthetic.main.activity_test1.*

@Route(path = "/arouter1/activity1")
class TestActivity1 : AppCompatActivity() {


    @Autowired
    @JvmField
    var name: String? = null


    @Autowired
    @JvmField
    var age: Int = 0


    @Autowired(name = "boy", required = true)
    @JvmField
    var gender: Boolean = false


    @Autowired
    @JvmField
    var high: Long = 0


    @Autowired
    @JvmField
    var url: String? = null


    @Autowired
    @JvmField
    var ser: TestSerializable? = null


    @Autowired
    @JvmField
    var pac: TestParcelable? = null


    @Autowired
    @JvmField
    var obj: TestObj? = null

    @Autowired
    @JvmField
    var objList: List<TestObj>? = null


    @Autowired
    @JvmField
    var map: Map<String, List<TestObj>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
        ARouter.getInstance().inject(this)

        var param = String.format(
            "name = %s,\nage = %s,\ngender = %s,\nhigh== %s," +
                    "\nurl= %s,\nser= %s,\npac= %s,\nobj= %s,\nobjList= %s,\nmap= %s",
            name,
            age,
            gender,
            high,
            url,
            ser,
            pac,
            obj,
            objList,
            map
        )

        tv_activity1_content.text = param

    }
}
