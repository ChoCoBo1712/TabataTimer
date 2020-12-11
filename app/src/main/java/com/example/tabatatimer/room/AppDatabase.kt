package com.example.tabatatimer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabatatimer.room.dao.SequenceDao
import com.example.tabatatimer.room.dao.SequenceTimerCrossRefDao
import com.example.tabatatimer.room.dao.TimerDao
import com.example.tabatatimer.room.entities.Sequence
import com.example.tabatatimer.room.entities.SequenceTimerCrossRef
import com.example.tabatatimer.room.entities.Timer

@Database(entities = [Timer::class, Sequence::class, SequenceTimerCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
    abstract fun sequenceDao(): SequenceDao
    abstract fun sequenceTimerCrossRefDao(): SequenceTimerCrossRefDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "app_db")
            .build()
    }
}