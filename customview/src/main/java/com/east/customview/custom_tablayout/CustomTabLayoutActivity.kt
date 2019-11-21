package com.east.customview.custom_tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_custom_tab_layout.*

class CustomTabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_tab_layout)

        var list = ArrayList<String>()
        list.add("1111")
        list.add("11111111")
        list.add("111111")
        list.add("1111")
        list.add("11111")
        list.add("111")
        list.add("111111")
        list.add("1111111111111")


        tablayout.setAdapter(object :BaseAdapter(){
            override fun getCount(): Int {
                return list.size
            }

            override fun getView(position: Int, parent: ViewGroup): View {
                val view = LayoutInflater.from(this@CustomTabLayoutActivity)
                    .inflate(R.layout.item_tag, parent, false)
                val tagTv = view.findViewById<TextView>(R.id.tag_tv)
                tagTv.text = list[position]

                view.setOnClickListener {
                    Toast.makeText(this@CustomTabLayoutActivity,list[position],Toast.LENGTH_SHORT).show()
                }

                return view
            }

        })
    }
}
