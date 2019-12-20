package com.east.architecture_components.room.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  如果是java类,定义字段的时候必须包含字段的set和get方法
 *  @author: East
 *  @date: 2019-08-03
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "user")
data class RoomUser (
    @PrimaryKey(autoGenerate=true)
    var uid : Int,
    @ColumnInfo( name = "first_name")
    var firstName : String?,

    @ColumnInfo( name = "last_name")
    var lastName : String?
)