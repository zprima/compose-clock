package com.example.composeclock.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.composeclock.util.degrees
import com.example.composeclock.util.radiansPerHour
import com.example.composeclock.util.radiansPerTick

@Composable
fun Circular1(hours: Int, minutes: Int, seconds: Int){
    val hoursAngle =
        remember(hours) { (hours * radiansPerHour).toFloat() - (Math.PI / 2).toFloat() }
    val minutesAngle =
        remember(minutes) { (minutes * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }
    val secondsAngle =
        remember(seconds) { (seconds * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }

    val thickness = 10.dp


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                // seconds
                drawArc(
                    color = Color.Red,
                    startAngle = -90f,
                    sweepAngle = degrees(secondsAngle).toFloat() + 90f,
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                    size = Size(400f, 400f),
                    topLeft = Offset(
                        x = center.x - 200,
                        y = center.y - 200
                    )

                )

                // minutes
                drawArc(
                    color = Color.Green,
                    startAngle = -90f,
                    sweepAngle = degrees(minutesAngle).toFloat() + 90f,
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                    size = Size(300f, 300f),
                    topLeft = Offset(
                        x = center.x - 150,
                        y = center.y - 150
                    )
                )

                // hours
                drawArc(
                    color = Color.Blue,
                    startAngle = -90f,
                    sweepAngle = degrees(hoursAngle).toFloat() + 90f,
                    useCenter = false,
                    style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                    size = Size(200f, 200f),
                    topLeft = Offset(
                        x = center.x - 100,
                        y = center.y - 100
                    )
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
}