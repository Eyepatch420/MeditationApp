package com.example.meditationapp.ui.theme

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.*
import kotlin.math.abs

fun Path.standardQuadTo(from: Offset, to: Offset)
{
    quadraticTo(from.x, from.y, abs(from.x + to.x) / 2f, abs(from.y + to.y) / 2f)
}