package com.east.customview.custom_car_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_car_home.*
import java.util.*

class CarHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_home)

        var mItems = ArrayList<String>()

        for (i in 0..199) {
            mItems.add("i -> $i")
        }

        list_view.adapter = object :BaseAdapter(){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val item =
                    LayoutInflater.from(this@CarHomeActivity)
                        .inflate(R.layout.item_verticaldrag_lv, parent, false) as TextView
                item.text = mItems[position]
                return item
            }

            override fun getItem(position: Int): Any{
                return mItems[position]
            }

            override fun getItemId(position: Int): Long {
                return 0
            }

            override fun getCount(): Int {
                return mItems.size
            }

        }

    }
}
