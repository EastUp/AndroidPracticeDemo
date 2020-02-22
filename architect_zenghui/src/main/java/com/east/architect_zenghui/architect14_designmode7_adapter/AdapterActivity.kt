package com.east.architect_zenghui.architect14_designmode7_adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect14_designmode7_adapter.simple4.Adapter
import kotlinx.android.synthetic.main.activity_adapter.*

class AdapterActivity : AppCompatActivity() {

    var mItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)
//        var animator = ObjectAnimator.ofFloat(tv,"ScaleX",0f,0.5f,1f)
//        animator.duration = 3000
//        animator.start()

        for (i in 0..99){
            mItems.add("$i")
        }

//        for (item in mItems) {
//            var textView = View.inflate(this,R.layout.item_eastriselistview,null) as TextView
//            textView.text = item
//            listview.addView(textView)
//        }

        var adapter = Adapter(mItems)
        listview.setAdapter(adapter)

    }
}
