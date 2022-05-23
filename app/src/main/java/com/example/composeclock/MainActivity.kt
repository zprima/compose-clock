package com.example.composeclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeclock.ui.theme.ComposeClockTheme
import com.example.composeclock.util.radiansPerHour
import com.example.composeclock.util.radiansPerTick
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeClockTheme {
                val viewModel: ClockViewModel = viewModel()

                ClockApp(viewModel)
            }
        }
    }
}

@Composable
fun ClockApp(viewModel: ClockViewModel) {
    val clockState by viewModel.clockState.collectAsState()

    Surface(Modifier.fillMaxSize()) {
        ClockWatchFace(
            hours = clockState.hours,
            minutes = clockState.minutes,
            seconds = clockState.seconds,
        )
    }
}

@Composable
fun ClockWatchFace(hours: Int, minutes: Int, seconds: Int) {
    val hoursAngle =
        remember(hours) { (hours * radiansPerHour).toFloat() - (Math.PI / 2).toFloat() }
    val minutesAngle =
        remember(minutes) { (minutes * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }
    val secondsAngle =
        remember(seconds) { (seconds * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }

        val center = Offset(maxWidthPx / 2, maxHeightPx / 2)

        Canvas(Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Black,
                center = center,
                radius = 10f
            )

            drawCircle(
                color = Color.Black,
                center = center,
                radius = maxWidthPx / 2,
                style = Stroke(1f)
            )

            // hours
            val hoursEnd = center + Offset(cos(hoursAngle) * 0.8f, sin(hoursAngle) * 0.8f) * (maxWidthPx / 2)
            drawLine(
                color = Color.Black,
                start = center,
                end = hoursEnd, //Offset(center.x, center.y - (maxWidthPx / 2)),
                strokeWidth = 5f
            )

            // minutes
            val minutesEnd =
                center + Offset(cos(minutesAngle), sin(minutesAngle)) * (maxWidthPx / 2)
            drawLine(
                color = Color.Blue,
                start = center,
                end = minutesEnd, //Offset(center.x, center.y + (maxWidthPx / 2))
                strokeWidth = 3f
            )

            // seconds
            val secondsEnd =
                center + Offset(cos(secondsAngle), sin(secondsAngle)) * (maxWidthPx / 2)
            drawLine(
                color = Color.Red,
                start = center,
                end = secondsEnd
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("$hours:$minutes:$seconds")
        }
    }
}

