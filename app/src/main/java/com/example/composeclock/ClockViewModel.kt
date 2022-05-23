package com.example.composeclock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class ClockViewModel: ViewModel() {
    private val _clockState = MutableStateFlow(ClockState(0,0,0))
    val clockState = _clockState

    var uiState by mutableStateOf(UiState())
        private set

    init {
        startClock()
    }

    fun changeWatchface(){
        when(uiState.clockMode){
            ClockMode.ANALOG_1 ->
                uiState = uiState.copy(clockMode = ClockMode.ANALOG_2)
            ClockMode.ANALOG_2 ->
                uiState = uiState.copy(clockMode = ClockMode.ANALOG_1)
        }

    }

    private fun startClock(){
        viewModelScope.launch {
            while(true){
                val cal = Calendar.getInstance()
                val hours = cal.get(Calendar.HOUR)
                val minutes = cal.get(Calendar.MINUTE)
                val seconds = cal.get(Calendar.SECOND)

                _clockState.emit(ClockState(hours, minutes, seconds))

                val milliseconds = cal.get(Calendar.MILLISECOND)
                delay(1000L - milliseconds)
            }
        }
    }
}