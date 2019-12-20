package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.east.databinding.databinding.ActivityTest5Binding

/**
 *  事件绑定
 */
class Test5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest5Binding = DataBindingUtil.setContentView<ActivityTest5Binding>(this, R.layout.activity_test5)
        activityTest5Binding.presenter = Presenter()
    }


    inner class Presenter{
        //方法引用,签名必须和事件回调方法签名一直,即参数和返回值都得一致
        fun onClick(v: View){
            Toast.makeText(this@Test5Activity,"Method References方法引用提示 ", Toast.LENGTH_SHORT).show()
        }

        //监听器绑定,只要求返回值一致,不要求参数一致
        fun onLongClickListener(v: View, name:String):Boolean{
            Toast.makeText(this@Test5Activity,"长按提示 Hello$name", Toast.LENGTH_SHORT).show()
            return true
        }

        fun onListenerBindingClick(){
            Toast.makeText(this@Test5Activity,"Listener Bindings监听器绑定提示", Toast.LENGTH_SHORT).show()
        }
    }
}
