package com.example.tabatatimer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class Timer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val preparation: Int,

    val workout: Int,

    val rest: Int,

    val cycles: Int
)
