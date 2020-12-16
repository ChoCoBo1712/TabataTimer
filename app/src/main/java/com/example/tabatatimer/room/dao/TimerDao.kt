package com.example.tabatatimer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabatatimer.room.entities.Timer

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timer: Timer)

    @Delete
    fun delete(timer: Timer)

    @Query("SELECT * FROM timer ORDER BY title ASC")
    fun getAll(): LiveData<List<Timer>>

    @Query("SELECT * FROM timer WHERE timer_id = :id")
    fun get(id: Int) : Timer
}