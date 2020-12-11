package com.example.tabatatimer.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SequencesWithTimers(
    @Embedded val sequence: Sequence,

    @Relation(
        parentColumn = "sequenceId",
        entityColumn = "timerId",
        associateBy = Junction(SequenceTimerCrossRef::class)
    )
    val timers: List<Timer>
)
