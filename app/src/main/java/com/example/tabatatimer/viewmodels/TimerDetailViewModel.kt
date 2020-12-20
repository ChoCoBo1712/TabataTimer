package com.example.tabatatimer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerDetailViewModel(
    var title: MutableLiveData<String> = MutableLiveData<String>().apply { postValue("") },
    var preparation: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) },
    var workout: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(1) },
    var rest: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) },
    var cycles: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }
) : ViewModel() {


}

