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
}