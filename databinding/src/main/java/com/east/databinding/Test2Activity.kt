package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import com.east.databinding.databinding.ActivityTest2Binding
import com.east.databinding.entity.ObservableFieldFGoods
import kotlin.random.Random

/**
 *  测试ObservableFied
 */
class Test2Activity : AppCompatActivity() {

    lateinit var goods:ObservableFieldFGoods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest2Binding = setContentView<ActivityTest2Binding>(this, R.layout.activity_test2)

        goods = ObservableFieldFGoods(ObservableField("嘿嘿"),
            ObservableField("dasd"), ObservableFloat(30f)
        )

        activityTest2Binding.goods = goods
    }

    fun change(view : View){
        goods.name.set("name${Random.nextInt(100)}")
        goods.details.set("details${Random.nextInt(100)}")
        goods.price.set(Random.nextInt(100).toFloat())
    }
}
