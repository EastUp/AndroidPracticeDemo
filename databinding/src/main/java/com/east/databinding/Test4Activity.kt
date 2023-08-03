package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import com.east.databinding.databinding.ActivityTest4Binding
import com.east.databinding.entity.Goods
import com.east.databinding.entity.ObservableFieldFGoods

/**
 *  双向事件绑定
 */
class Test4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest4Binding = DataBindingUtil.setContentView<ActivityTest4Binding>(this, R.layout.activity_test4)
        activityTest4Binding.goods = ObservableFieldFGoods(ObservableField<String>("哈哈哈"),
            ObservableField<String>("饮料"),ObservableFloat(25f))
    }
}
