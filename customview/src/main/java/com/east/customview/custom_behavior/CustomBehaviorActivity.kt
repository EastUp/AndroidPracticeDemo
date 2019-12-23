package com.east.customview.custom_behavior

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.east.customview.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_custom_behavior.*

class CustomBehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_behavior)
        setSupportActionBar(tool_bar)

        fab.setOnClickListener {
            Snackbar.make(it,"Data deleted",Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(
                        this@CustomBehaviorActivity,
                        "Data restored",
                        Toast.LENGTH_SHORT
                    ).show()
                }.show()
        }
        recycler_view.adapter = object :RecyclerView.Adapter<MyViewHolder>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_behavior, parent, false)
//                View.inflate(parent.context,R.layout.item_behavior,parent)
                return MyViewHolder(view)
            }

            override fun getItemCount() = 30

            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            }

        }
    }


    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    }
}
