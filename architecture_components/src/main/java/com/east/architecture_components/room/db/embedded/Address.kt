package com.east.architecture_components.room.db.embedded

import androidx.room.ColumnInfo

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 作为被嵌套的对象嵌入User中
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class Address(val street : String?,
                   val state : String?,
                   val city : String?,
                   @ColumnInfo(name = "post_code")
                   val postCode_Int : Int)