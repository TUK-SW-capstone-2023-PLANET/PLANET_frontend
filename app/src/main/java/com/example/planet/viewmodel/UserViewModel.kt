package com.example.planet.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    val idTextValue = mutableStateOf("")
    val pwTextValue = mutableStateOf("")

    val duplicateCheck by derivedStateOf {
        idTextValue.value.isNotEmpty()
    }

}