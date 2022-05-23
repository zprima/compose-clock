package com.example.composeclock.util

fun radians(value:Float) = value * Math.PI/180
// fun degrees(value:Float) = value * 180/Math.PI

val radiansPerTick = radians(360f / 60f)
val radiansPerHour = radians(360f / 12f)