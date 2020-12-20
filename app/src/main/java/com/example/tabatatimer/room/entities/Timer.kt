package com.example.tabatatimer.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class Timer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id")
    var id: Int = 0,

    var title: String,

    var preparation: Int,

    var workout: Int,

    var rest: Int,

    var cycles: Int
)
