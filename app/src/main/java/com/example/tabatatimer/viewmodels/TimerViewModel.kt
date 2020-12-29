package com.example.tabatatimer.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel

data class TimerViewModel(
    var title: String = "",
    var preparation: Int = 1,
    var workout: Int = 1,
    var rest: Int = 1,
    var phase: Int = 0,
    var cycle: Int = 1,
    var cycles: Int = 1,
    var isRunning: Boolean = false,
) : ViewModel()
