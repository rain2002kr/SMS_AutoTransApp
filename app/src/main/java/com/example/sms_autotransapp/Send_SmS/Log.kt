package com.example.roomexample_yena

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logTest")
class Log (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name =  "message")
    var message: String


){

    constructor() : this(null,""
    )
}