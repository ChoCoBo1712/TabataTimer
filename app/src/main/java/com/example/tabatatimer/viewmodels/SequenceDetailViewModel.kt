package com.example.tabatatimer.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tabatatimer.room.entities.Timer

data class SequenceDetailViewModel(
        var id: Int = 0,
        var title: String = "",
        var colour: Int = 0,
        var timers: List<Timer> = emptyList()
) : ViewModel()