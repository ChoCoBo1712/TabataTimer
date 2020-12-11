package com.example.tabatatimer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "sequence_timer",
    primaryKeys = ["sequenceId", "timerId"]
)
data class SequenceTimerCrossRef(
    @ColumnInfo(name = "sequence_id")
    val sequenceId: Int,

    @ColumnInfo(name = "timer_id")
    val timerId: Int
)
