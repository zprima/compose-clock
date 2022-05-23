package com.example.composeclock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class ClockViewModel: ViewModel() {
    private val _clockState = MutableStateFlow(ClockUiState(0,0,0))
    val clockState = _clockState

    init {
        startClock()
    }

    private fun startClock(){
        viewModelScope.launch {
            while(true){
                val cal = Calendar.getInstance()
                val hours = cal.get(Calendar.HOUR)
                val minutes = cal.get(Calendar.MINUTE)
                val seconds = cal.get(Calendar.SECOND)

                _clockState.emit(ClockUiState(hours, minutes, seconds))

                val milliseconds = cal.get(Calendar.MILLISECOND)
                delay(1000L - milliseconds)
            }
        }
    }
}