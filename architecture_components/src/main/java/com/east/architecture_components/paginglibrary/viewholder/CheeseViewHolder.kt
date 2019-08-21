package com.east.architecture_components.paginglibrary.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.east.architecture_components.R
import com.east.architecture_components.paginglibrary.entity.Cheese

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CheeseViewHolder(parent:ViewGroup)  : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.cheese_item,parent,false)
) {
    private val nameView = itemView.findViewById<TextView>(R.id.name)
    var cheese: Cheese ?= null

    fun bindTo(cheese: Cheese?){
        this.cheese = cheese
        nameView.text = cheese?.name
    }
}