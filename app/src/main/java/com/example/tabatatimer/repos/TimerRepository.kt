package com.example.tabatatimer.repos

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.tabatatimer.SingletonHolder
import com.example.tabatatimer.TimerDetailFragment
import com.example.tabatatimer.room.AppDatabase
import com.example.tabatatimer.room.entities.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TimerRepository private constructor(context: Context) {

    private val db = AppDatabase(context)
    private var timers: LiveData<List<Timer>> = db.timerDao().getAll()

    fun getAll(): LiveData<List<Timer>> {
        return timers
    }

    suspend fun insert(timer: Timer) {
        return withContext(Dispatchers.IO) {
            db.timerDao().insert(timer)
        }
    }

    companion object : SingletonHolder<TimerRepository, Context>(::TimerRepository)
}