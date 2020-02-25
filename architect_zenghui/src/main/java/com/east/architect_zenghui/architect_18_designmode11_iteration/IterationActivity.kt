package com.east.architect_zenghui.architect_18_designmode11_iteration

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.MainBottomTabItem
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.BottomTabItem
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.OnTabItemViewClickListener
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.iterator.TabListIterator
import kotlinx.android.synthetic.main.activity_iteration.*

class IterationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iteration)

        var tabListIterator = TabListIterator()
        tabListIterator.addTabItem(MainBottomTabItem.Builder(this)
            .text("text1")
            .resIcon(R.drawable.main_tab_item)
            .create())
        tabListIterator.addTabItem(MainBottomTabItem.Builder(this)
            .text("text2")
            .resIcon(R.drawable.main_tab_item)
            .create())
        tabListIterator.addTabItem(MainBottomTabItem.Builder(this)
            .text("text3")
            .resIcon(R.drawable.main_tab_item)
            .create())

        tablayout.addBottomTabItems(tabListIterator)

        tablayout.setOnTabItemViewClickListener(object :OnTabItemViewClickListener{
            override fun onClick(index: Int, bottomTabItem: BottomTabItem) {
                Toast.makeText(this@IterationActivity,"$index",Toast.LENGTH_SHORT).show()
            }
        })

    }
}
