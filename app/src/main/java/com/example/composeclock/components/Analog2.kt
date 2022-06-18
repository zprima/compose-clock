package com.example.composeclock.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import com.example.composeclock.util.radiansPerHour
import com.example.composeclock.util.radiansPerTick
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Analog2(hours: Int, minutes: Int, seconds: Int){
    val secondsAngle =
        remember(seconds) { (seconds * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }
    val secondsPrevAngle =
        remember(seconds) { ((seconds - 1) * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }
    val secondsNextAngle =
        remember(seconds) { ((seconds + 1) * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }

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

            drawCircle(
                color = Color.Black,
                center = center,
                radius = maxWidthPx / 2 * 0.8f,
                style = Stroke(1f)
            )

            val secondsPrevEnd = center + Offset(cos(secondsPrevAngle), sin(secondsPrevAngle)) * (maxWidthPx / 2)
            val secondsPrevStart = center + Offset(cos(secondsPrevAngle), sin(secondsPrevAngle)) * (maxWidthPx / 2 * 0.8f)

            val secondsNextEnd = center + Offset(cos(secondsNextAngle), sin(secondsNextAngle)) * (maxWidthPx / 2)
            val secondsNextStart = center + Offset(cos(secondsNextAngle), sin(secondsNextAngle)) * (maxWidthPx / 2 * 0.8f)

            val secondsEnd = center + Offset(cos(secondsAngle), sin(secondsAngle)) * (maxWidthPx / 2)
            val secondsStart = center + Offset(cos(secondsAngle), sin(secondsAngle)) * (maxWidthPx / 2 * 0.8f)


            val path = Path().apply {
                moveTo(secondsPrevStart.x, secondsPrevStart.y)
                lineTo(secondsStart.x, secondsStart.y)
                lineTo(secondsNextStart.x, secondsNextStart.y)

                lineTo(secondsNextEnd.x, secondsNextEnd.y)
                lineTo(secondsEnd.x, secondsEnd.y)
                lineTo(secondsPrevEnd.x, secondsPrevEnd.y)
                lineTo(secondsPrevStart.x, secondsPrevStart.y)
            }

            drawPath(
                path,
                Color.Magenta,
                style = Fill
            )

            drawLine(Color.Blue, secondsStart, secondsEnd,10f)


            (1..60).forEachIndexed { index, number ->
                val angle = (index * radiansPerTick).toFloat() - (Math.PI / 2).toFloat()
                val calculatedEnd =
                    center + Offset(cos(angle),sin(angle)) * (maxWidthPx / 2)

                drawLine(
                    color = Color.Black,
                    start = center,
                    end = calculatedEnd,
                    strokeWidth = 1f
                )
            }

            (1..12).forEachIndexed { index, number ->
                val angle = (index * radiansPerHour).toFloat() - (Math.PI / 2).toFloat()
                val calculatedEnd =
                    center + Offset(cos(angle),sin(angle)) * (maxWidthPx / 2)

                drawLine(
                    color = Color.Red,
                    start = center,
                    end = calculatedEnd,
                    strokeWidth = 5f
                )
            }




//            // hours
//            val hoursEnd = center + Offset(cos(hoursAngle) * 0.8f, sin(hoursAngle) * 0.8f) * (maxWidthPx / 2)
//            drawLine(
//                color = Color.Black,
//                start = center,
//                end = hoursEnd, //Offset(center.x, center.y - (maxWidthPx / 2)),
//                strokeWidth = 5f
//            )
//
//            // minutes
//            val minutesEnd =
//                center + Offset(cos(minutesAngle), sin(minutesAngle)) * (maxWidthPx / 2)
//            drawLine(
//                color = Color.Blue,
//                start = center,
//                end = minutesEnd, //Offset(center.x, center.y + (maxWidthPx / 2))
//                strokeWidth = 3f
//            )
//
//            // seconds
//            val secondsEnd =
//                center + Offset(cos(secondsAngle), sin(secondsAngle)) * (maxWidthPx / 2)
//            drawLine(
//                color = Color.Red,
//                start = center,
//                end = secondsEnd
//            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("$hours:$minutes:$seconds")
        }
    }
}