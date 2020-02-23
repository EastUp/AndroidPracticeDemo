package com.east.architect_zenghui.architect15_designmode8_observe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect15_designmode8_observe.simple4.*
import kotlinx.android.synthetic.main.activity_observe.*

class ObserveActivity : AppCompatActivity() ,Observer<Member>{

    var mMembers = ArrayList<Member>()
    var mAdapter : BaseAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observe)

        DatabaseManager.getInstance().register(this)

        if(mAdapter == null){
            mAdapter = object:BaseAdapter(){
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    // 去掉界面复用优化
                    var textView = LayoutInflater.from(parent.context).inflate(R.layout.item_observe,parent,false) as TextView
                    textView.text = mMembers[position].name
                    return textView
                }
                override fun getItem(position: Int): Any = mMembers[position]
                override fun getItemId(position: Int): Long  = position.toLong()
                override fun getCount(): Int  = mMembers.size
            }
        }
        lv.adapter = mAdapter

    }

    override fun onRestart() {
        super.onRestart()
        //// 查询数据库更新列表
    }

    fun addMember(v: View){
        startActivity(Intent(this,InsuranceActivity::class.java))
    }

    override fun update(observable: Observable<*, out Observer<*>>?, data: Member) {
        mMembers.add(data)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        DatabaseManager.getInstance().unregister(this)
        super.onDestroy()
    }
}
