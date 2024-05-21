package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(): ViewModel() {

    var userId:Long = 0

    var dialogState by mutableStateOf(false)
    var messageInput by mutableStateOf("")
}