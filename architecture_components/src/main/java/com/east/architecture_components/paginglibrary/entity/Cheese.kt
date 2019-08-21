package com.east.architecture_components.paginglibrary.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-12
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity
data class Cheese(@PrimaryKey(autoGenerate = true) val id : Int,val name : String)