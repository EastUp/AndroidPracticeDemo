package com.east.customview.huashu_loading

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import com.east.customview.click.ClickUtils
import kotlinx.android.synthetic.main.activity_hua_shu_loading.*

class HuaShuLoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hua_shu_loading)
    }

    fun onClick(v: View){
        if(!ClickUtils.notQuickClick())
            return
        if(btn_switch.text == "关闭"){
            loading_view.visibility = View.GONE
            btn_switch.text = "开启"
        } else{
            loading_view.visibility = View.VISIBLE
            btn_switch.text = "关闭"
        }
    }

    override fun finish() {
        super.finish()
        loading_view.visibility = View.GONE
    }
}
