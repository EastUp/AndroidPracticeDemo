package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.*
import com.east.databinding.databinding.ActivityTest3Binding
import kotlin.random.Random

/**
 * ObservableList 和 ObservableMap
 */
class Test3Activity : AppCompatActivity() {

    lateinit var list : ObservableList<String>
    lateinit var map : ObservableMap<String,String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest3Binding = DataBindingUtil.setContentView<ActivityTest3Binding>(this, R.layout.activity_test3)
        list = ObservableArrayList<String>()
        list.add("list")

        map = ObservableArrayMap<String,String>()
        map["key"] = "map"

        activityTest3Binding.list = list
        activityTest3Binding.map = map
        activityTest3Binding.key = "key"

    }

    fun change(view : View){
        list[0] = "list${Random.nextInt(100)}"
        map["key"] = "map${Random.nextInt(100)}"
    }
}
