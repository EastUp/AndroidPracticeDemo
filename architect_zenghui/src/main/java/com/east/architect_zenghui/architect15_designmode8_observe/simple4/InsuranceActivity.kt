package com.east.architect_zenghui.architect15_designmode8_observe.simple4

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_insurance.*

class InsuranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insurance)
    }

    fun add(v: View){
        var member = Member(et_name.text.toString(),et_age.text.toString())
        //插入数据的时候通知到观察者
        DatabaseManager.getInstance().insert(member)
    }

    fun finish(v: View){
        finish()
    }


}
