package com.example.planet.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.component.main.MainTopSwitch
import com.example.planet.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        MainTopSwitch(mainViewModel = mainViewModel)
    }
}