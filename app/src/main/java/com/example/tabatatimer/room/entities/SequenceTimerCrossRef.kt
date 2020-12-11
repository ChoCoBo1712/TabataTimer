package com.example.tabatatimer.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "sequence_timer",
    primaryKeys = ["sequence_id", "timer_id"]
)
data class SequenceTimerCrossRef(
    @ColumnInfo(name = "sequence_id")
    val sequenceId: Int,

    @ColumnInfo(name = "timer_id")
    val timerId: Int
)
