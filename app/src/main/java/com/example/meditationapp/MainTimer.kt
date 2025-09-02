package com.example.meditationapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TimerScreen(totalTimeInMillis: Long) {

    val viewModel: TimerViewModel = viewModel()
    val scope = rememberCoroutineScope()

    LaunchedEffect(totalTimeInMillis) {
        if (viewModel.currentTime.value == 0L){
            viewModel.setInitialTime(totalTimeInMillis)
        }
    }

    val currentTime by viewModel.currentTime.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val isTimeRunning by viewModel.isTimeRunning.collectAsState()


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF9FA5FE))
            .onSizeChanged { size -> viewModel.updateSize(size) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // Time display
            val minutes = (currentTime / 1000L) / 60
            val seconds = (currentTime / 1000L) % 60
            Text(
                text = "%02d:%02d".format(minutes, seconds),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF3F51B5),
                trackColor = Color.LightGray,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Pause/Start button
            Button(onClick = {
                scope.launch {
                    viewModel.toggleTimer()
                }
            }) {
                Text(if (isTimeRunning) "Pause" else "Start")
            }
        }
    }
}