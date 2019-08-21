package com.east.architecture_components.room.db.embedded

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 嵌套对象
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */

@Entity(tableName = "embedded")
data class EmbeddedUser(@PrimaryKey val id :Int,
                        @ColumnInfo(name = "first_name") val firstName : String?,
                        @Embedded(prefix = "address_") val address : Address
)