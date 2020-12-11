package com.example.tabatatimer.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sequence")
data class Sequence(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val colour: Int
)
