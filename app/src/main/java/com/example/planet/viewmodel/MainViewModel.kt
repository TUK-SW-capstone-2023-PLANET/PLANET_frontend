package com.example.planet.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _ploggingOrRecordSwitch = mutableStateOf(true)
    val ploggingOrRecordSwitch: State<Boolean> = _ploggingOrRecordSwitch


    fun changePloggingScreen() {
        _ploggingOrRecordSwitch.value = false
    }
    fun changeRecordScreen() {
        _ploggingOrRecordSwitch.value = true
    }

}