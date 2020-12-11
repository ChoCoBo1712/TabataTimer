package com.example.tabatatimer.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tabatatimer.room.entities.SequenceTimerCrossRef

@Dao
interface SequenceTimerCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sequenceTimerCrossRef: SequenceTimerCrossRef) : Long

    @Query("DELETE FROM sequence_timer WHERE sequence_id = :id")
    fun deleteBySequenceId(id: Int)

    @Query("DELETE FROM sequence_timer WHERE timer_id = :id")
    fun deleteByTimerId(id: Int)
}