package com.example.tabatatimer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext

class TimerService : Service() {

    private val TAG = "TimerService"
    private val bundle = Bundle()
    private lateinit var timer: CountDownTimer
    private var phase = 0
    private var cycle = 1
    private var counter = 1
    private var preparation = 1
    private var workout = 1
    private var rest = 1
    private var cycles = 1
    private var isRunning = false

    init {
        Log.d(TAG, "Service is running")
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val command = intent.getStringExtra("command")
            if (command == null) {
                preparation = intent.getIntExtra("preparation", 0)
                workout = intent.getIntExtra("workout", 1)
                rest = intent.getIntExtra("rest", 1)
                cycles = intent.getIntExtra("cycles", 1)
                counter = preparation
            }

            if (command == "playPause") {
                if (!isRunning) {
                    isRunning = true
                    startTimer(counter.toLong())
                    bundle.putString("image", "pause")
                }
                else {
                    isRunning = false
                    pauseTimer()
                    bundle.putString("image", "play")
                }
                bundle.putString("message", "playPause")
                sendBroadcast(bundle)
                bundle.clear()
            }
            else if (command == "next") {
                if (phase < 2) {
                    if (isRunning) {
                        pauseTimer()
                        phase += 1
                        phaseTime(phase)
                        startTimer(counter.toLong())
                    }
                    else {
                        phase += 1
                        phaseTime(phase)
                        bundle.putString("message", "arrow")
                        bundle.putInt("counter", counter)
                        sendBroadcast(bundle)
                        bundle.clear()
                    }
                }
            }
            else if (command == "prev") {
                if (phase > 0) {
                    if (isRunning) {
                        pauseTimer()
                        phase -= 1
                        phaseTime(phase)
                        startTimer(counter.toLong())
                    }
                    else {
                        phase -= 1
                        phaseTime(phase)
                        bundle.putString("message", "arrow")
                        bundle.putInt("counter", counter)
                        sendBroadcast(bundle)
                        bundle.clear()
                    }
                }
            }
        }

        return START_NOT_STICKY
    }

    private fun startTimer(time: Long) {
        timer = object : CountDownTimer(time * 1000, 1000) {
            override fun onFinish() {
                if (phase < 2) {
                    val mediaPlayer = MediaPlayer.create(this@TimerService, R.raw.ring)
                    mediaPlayer.start()

                    phase += 1
                    phaseTime(phase)
                    startTimer(counter.toLong())
                }
                else {
                    if (cycle < cycles) {
                        val mediaPlayer = MediaPlayer.create(this@TimerService, R.raw.ring)
                        mediaPlayer.start()

                        cycle += 1
                        bundle.putString("message", "cycles")
                        bundle.putInt("cycle", cycle)
                        sendBroadcast(bundle)
                        bundle.clear()
                        phase = 0
                        counter = preparation
                        startTimer(counter.toLong())
                    }
                    else {
                        val mediaPlayer = MediaPlayer.create(this@TimerService, R.raw.finish)
                        mediaPlayer.start()

                        isRunning = false
                        cycle = 1
                        phase = 0
                        counter = preparation
                        bundle.putString("message", "finished")
                        sendBroadcast(bundle)
                        bundle.clear()
                    }
                }
            }

            override fun onTick(timeLeft: Long) {
                var left = (timeLeft / 1000) + 1
                if (left > counter) {
                    left -= 1
                }
                bundle.putString("message", "tick")
                bundle.putLong("left", left)
                sendBroadcast(bundle)
                bundle.clear()
                counter = left.toInt()
            }
        }
        timer.start()
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun phaseTime(phaseInt: Int) {
        when (phaseInt) {
            0 -> {
                bundle.putInt("phase", R.string.preparation)
                counter = preparation
            }
            1 -> {
                bundle.putInt("phase", R.string.workout)
                counter = workout
            }
            2 -> {
                bundle.putInt("phase", R.string.rest)
                counter = rest
            }
        }
        bundle.putString("message", "phase")
        sendBroadcast(bundle)
        bundle.clear()
    }

    private fun sendBroadcast(bundle: Bundle) {
        val intent = Intent()
        intent.action = "message"
        intent.putExtras(bundle)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "Service is stopped")

        super.onDestroy()
    }
}