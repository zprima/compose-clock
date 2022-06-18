package com.example.composeclock.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.composeclock.util.radiansPerHour
import com.example.composeclock.util.radiansPerTick
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
@Composable
fun Analog1(hours: Int, minutes: Int, seconds: Int) {
    val hoursAngle =
        remember(hours) { (hours * radiansPerHour).toFloat() - (Math.PI / 2).toFloat() }
    val minutesAngle =
        remember(minutes) { (minutes * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }
    val secondsAngle =
        remember(seconds) { (seconds * radiansPerTick).toFloat() - (Math.PI / 2).toFloat() }

    val infinityTransition = rememberInfiniteTransition()
    val colors = remember { listOf(Color.Cyan, Color.Magenta, Color.Cyan, Color.Magenta, Color.Cyan, Color.Magenta) }
    val colorAnimations = colors.mapIndexed { index, color ->
        val nextIndex = if (index+1 >= colors.size) 0 else index + 1

        infinityTransition.animateColor(
            initialValue = color,
            targetValue = colors[nextIndex],
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 700,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    val hBrush = Brush.horizontalGradient(colorAnimations.map { it.value })

    Column() {

        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
            val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }

            val center = Offset(maxWidthPx / 2, maxHeightPx / 2)
            val clockCenter = center - Offset(0f, 0f)
            val clockMaxWidth = (maxWidthPx / 2) - 200f

            Canvas(Modifier.fillMaxSize()) {
                drawCircle(
                    brush = hBrush,
                    center = clockCenter,
                    radius = clockMaxWidth + 50f,
                    style = Stroke(10f)
                )

                // hours
                val hoursEnd = clockCenter + Offset(
                    cos(hoursAngle) * 0.7f,
                    sin(hoursAngle) * 0.7f
                ) * clockMaxWidth
                drawLine(
                    color = Color.Black,
                    start = clockCenter,
                    end = hoursEnd, //Offset(center.x, center.y - (maxWidthPx / 2)),
                    strokeWidth = 18f
                )

                // minutes
                val minutesEnd =
                    clockCenter + Offset(cos(minutesAngle), sin(minutesAngle)) * clockMaxWidth
                drawLine(
                    color = Color.Black,
                    start = clockCenter,
                    end = minutesEnd, //Offset(center.x, center.y + (maxWidthPx / 2))
                    strokeWidth = 12f
                )

                // seconds
                val secondsEnd =
                    clockCenter + Offset(cos(secondsAngle), sin(secondsAngle)) * clockMaxWidth
                drawLine(
                    color = Color.Red,
                    start = clockCenter,
                    end = secondsEnd,
                    strokeWidth = 8f
                )

                drawCircle(
                    color = Color.Red,
                    center = clockCenter,
                    radius = 20f
                )

                drawCircle(
                    color = Color.White,
                    center = clockCenter,
                    radius = 10f
                )


            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle()) {
                        append(
                            "${hours.toString().padWithZeros()}:${
                                minutes.toString().padWithZeros()
                            }"
                        )
                    }

                    withStyle(
                        style = SpanStyle(
                            fontSize = 32.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    ) {
                        append(seconds.toString().padWithZeros())
                    }
                },
                fontSize = 64.sp,
            )
        }
    }
}

private fun String.padWithZeros(): String {
    return this.padStart(2, '0')
}


