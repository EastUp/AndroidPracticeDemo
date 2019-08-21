package com.east.databinding

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.*
import com.east.databinding.databinding.ActivityTest10Binding
import com.east.databinding.entity.ObservableFieldFGoods
import kotlinx.android.synthetic.main.activity_test10.*

/**
 *  BindingConversion
 */
class Test10Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest10Binding =
            DataBindingUtil.setContentView<ActivityTest10Binding>(this, R.layout.activity_test10)
        activityTest10Binding.goods = ObservableFieldFGoods(
            ObservableField("开玩笑"),
            ObservableField("密码"), ObservableFloat(0f)
        )
        tv_name.text = "嘻嘻"
    }


    object Presenter {

        //布局文件中所有@{String}的String进行处理
        @BindingConversion
        @JvmStatic
        open fun conversionString(str: String): String {
            return "$str--conversion--"
        }

        //布局文件中所有@{String}的String进行处理
        @BindingConversion
        @JvmStatic
        open fun conversionStringToDrawable(str: String): Int {
            return if (str == "红色")
                Color.parseColor("#FF4081")
            else
                Color.BLACK
        }

        //覆盖Android原先控件属性
        //两个参数解释:只有当textView控件的android:text属性发生变化时才会生效
        @BindingAdapter("android:text")
        @JvmStatic
        open fun changeText(view: TextView, str: String?) {
            view.text = str + "BindAdataper"
        }

    }
}
