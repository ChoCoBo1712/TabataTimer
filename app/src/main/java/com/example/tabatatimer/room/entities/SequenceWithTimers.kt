package com.example.tabatatimer.room.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SequenceWithTimers(
    @Embedded val sequence: Sequence,

    @Relation(
        parentColumn = "sequence_id",
        entityColumn = "timer_id",
        associateBy = Junction(SequenceTimerCrossRef::class)
    )
    val timers: List<Timer>
)
