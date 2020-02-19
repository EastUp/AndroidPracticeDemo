package com.east.architect_zenghui.architect11_Designmode4_decorator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect11_Designmode4_decorator.simple2_decorator.PersonEat
import com.east.architect_zenghui.architect11_Designmode4_decorator.simple2_decorator.TeacherEat
import kotlinx.android.synthetic.main.activity_decorator.*

class DecoratorActivity : AppCompatActivity() {

    var mItems = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decorator)

        for(i in 0..99){
            mItems.add(i)
        }

        // 一般的写法new对象调用方法
        val eat = PersonEat()
        val teacherEat = TeacherEat(eat)
        teacherEat.eat()
        // 装饰设计模式怎么写，一般情况都是把类对象作为构造参数传递

//        recyclerview.adapter = RecyclerAdapter()

        // 采用装饰设计模式，让其支持添加头部和底部
        // 添加头部 有没有问题？
//        val recyclerAdapter = RecyclerAdapter()
//        val wrapRecyclerAdapter = WrapRecyclerAdapter(recyclerAdapter)
//        recyclerview.adapter = wrapRecyclerAdapter
//        var view = LayoutInflater.from(this).inflate(R.layout.layout_header_view,recyclerview,false)
//        wrapRecyclerAdapter.addHeaderView(view)
        // 面向对象的六大基本原则在哪里？最少知识原则又在哪里呢？必须要像ListView让其支持
        // 不要把代码过度封装，在我看来，业务逻辑能分开就分开，底层和中间层不含业务逻辑的能封装就封装不用纠结过度封装

        recyclerview.adapter = RecyclerAdapter()
        var view = LayoutInflater.from(this).inflate(R.layout.layout_header_view,recyclerview,false)
        recyclerview.addHeaderView(view)
        recyclerview.addFooterView(view)
    }


    private inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount() = mItems.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTextView.text = "position = ${mItems[position]}"
            holder.mTextView.setOnClickListener {
                Log.e("TAG",position.toString())
                mItems.removeAt(position)
                notifyDataSetChanged()
            }
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mTextView = itemView.findViewById<TextView>(R.id.text)
        }
    }

}
