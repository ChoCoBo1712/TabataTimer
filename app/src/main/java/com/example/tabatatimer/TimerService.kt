package com.example.tabatatimer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TimerService : Service() {

    val TAG = "TimerService"

    init {
        Log.d(TAG, "Service is running")
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val preparation = intent?.getIntExtra("preparation", 0)
        val workout = intent?.getIntExtra("workout", 0)
        val rest = intent?.getIntExtra("rest", 0)
        val cycles = intent?.getIntExtra("cycles", 0)
        val command = intent?.getStringExtra("command")
        if (preparation != null && preparation != 0) {
            Log.d(TAG, preparation.toString())
            Log.d(TAG, workout.toString())
            Log.d(TAG, rest.toString())
            Log.d(TAG, cycles.toString())
        }
        else if (command != null) {
            Log.d(TAG, command)
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "Service is stopped")

        super.onDestroy()
    }
}