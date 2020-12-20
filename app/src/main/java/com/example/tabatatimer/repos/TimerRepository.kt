package com.example.tabatatimer.repos

import android.content.Context
import com.example.tabatatimer.room.AppDatabase
import com.example.tabatatimer.room.entities.Timer

class TimerRepository(context: Context) {

    private val db = AppDatabase(context)

    fun insert(timer: Timer) {
        suspend {
            db.timerDao().insert(timer)
        }
    }
}