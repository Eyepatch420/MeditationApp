package com.example.meditationapp

import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _currentTime = MutableStateFlow(0L)
    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _isTimeRunning = MutableStateFlow(false)
    val isTimeRunning: StateFlow<Boolean> = _isTimeRunning.asStateFlow()

    private var totalTimeInMillis: Long = 0L
    private var size: IntSize = IntSize.Zero

    fun setInitialTime(timeInMillis: Long) {
        totalTimeInMillis = timeInMillis
        _currentTime.value = timeInMillis
        _progress.value = 1f
        startTimer()
    }

    fun updateSize(newSize: IntSize) {
        size = newSize
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_isTimeRunning.value && _currentTime.value > 0L) {
                delay(100L)
                _currentTime.update { it - 100L }
                _progress.update { _currentTime.value / totalTimeInMillis.toFloat() }
            }
            if (_currentTime.value <= 0L) {
                _isTimeRunning.value = false
            }
        }
    }

    fun toggleTimer() {
        _isTimeRunning.value = !_isTimeRunning.value
        if (_isTimeRunning.value && _currentTime.value > 0L) {
            startTimer()
        }
    }
}