package com.example.planet.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class RecordViewModel @Inject constructor() : ViewModel() {
    var currentDate by mutableStateOf(LocalDate.now())
    var today by mutableStateOf(LocalDate.now())
    var selectedDate: LocalDate? by mutableStateOf(null)




}