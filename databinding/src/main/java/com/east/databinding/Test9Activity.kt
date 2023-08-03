package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import com.east.databinding.databinding.ActivityTest9Binding
import com.east.databinding.entity.ObservableFieldFGoods
import com.east.databinding.entity.User
import kotlinx.android.synthetic.main.activity_test9.*
import kotlin.random.Random

/**
 * BindingAdapter
 */
class Test9Activity : AppCompatActivity() {

    lateinit var goods:ObservableFieldFGoods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest9Binding =
            DataBindingUtil.setContentView<ActivityTest9Binding>(this, R.layout.activity_test9)
        goods = ObservableFieldFGoods(ObservableField("测试"), ObservableField(""), ObservableFloat(20f))
        activityTest9Binding.goods = goods
    }


    companion object{
        //覆盖Android原先控件属性
        //两个参数解释:只有当textView控件的android:text属性发生变化时才会生效
        @BindingAdapter("android:text")
        @JvmStatic
        fun changeText(view:TextView,str:String?){
            view.text = str + "BindAdataper"
        }

        @BindingAdapter("password")
        @JvmStatic
        fun changePassword(view:TextView,str:String?){
            view.text = str+"自定义属性password"
        }
    }

    fun onClick(view : View){
        goods.name.set("测试${Random.nextInt(100)}")
        goods.details.set("details${Random.nextInt(100)}")
        btn_change.text="改变${Random.nextInt(100)}"
    }
}
