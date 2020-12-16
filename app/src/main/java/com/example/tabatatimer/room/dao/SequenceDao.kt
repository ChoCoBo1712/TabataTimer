package com.example.tabatatimer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabatatimer.room.entities.Sequence
import com.example.tabatatimer.room.entities.SequenceWithTimers

@Dao
interface SequenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sequence: Sequence)

    @Delete
    fun delete(sequence: Sequence)

    @Query("SELECT * FROM sequence ORDER BY title ASC")
    fun getAll(): LiveData<List<SequenceWithTimers>>

    @Query("SELECT * FROM sequence WHERE sequence_id = :id")
    fun get(id: Int) : SequenceWithTimers
}