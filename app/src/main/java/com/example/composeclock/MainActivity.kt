package com.example.composeclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeclock.components.Analog1
import com.example.composeclock.components.Analog2
import com.example.composeclock.components.Circular1
import com.example.composeclock.ui.theme.ComposeClockTheme
import com.example.composeclock.util.radiansPerHour
import com.example.composeclock.util.radiansPerTick
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeClockTheme(darkTheme = false) {
                val viewModel: ClockViewModel = viewModel()

                ClockApp(viewModel)
            }
        }
    }
}

@Composable
fun ClockApp(viewModel: ClockViewModel) {
    val clockState by viewModel.clockState.collectAsState()
    val uiState = viewModel.uiState

    Surface(Modifier.fillMaxSize().clickable { viewModel.changeWatchface() }) {
        ClockWatchFace(
            hours = clockState.hours,
            minutes = clockState.minutes,
            seconds = clockState.seconds,
            mode = uiState.clockMode
        )
    }
}

@Composable
fun ClockWatchFace(hours: Int, minutes: Int, seconds: Int, mode: ClockMode) {
    when (mode) {
        ClockMode.ANALOG_1 -> Analog1(hours, minutes, seconds)
        ClockMode.ANALOG_2 -> Analog2(hours, minutes, seconds)
        ClockMode.CIRCULAR_1 -> Circular1(hours, minutes, seconds)
    }
}
