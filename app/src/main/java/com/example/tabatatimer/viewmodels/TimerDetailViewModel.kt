package com.example.tabatatimer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerDetailViewModel(
        var id: Int = 0,
        var title: String = "",
        var preparation: Int = 0,
        var workout: Int = 1,
        var rest: Int = 0,
        var cycles: Int = 1
) : ViewModel() {


}

