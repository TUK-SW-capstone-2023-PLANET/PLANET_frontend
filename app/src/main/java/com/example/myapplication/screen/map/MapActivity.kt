package com.example.myapplication.screen.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.MainApplication
import com.example.myapplication.TAG
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MapActivity : ComponentActivity() {
    private val mapViewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                MapGraph(mapViewModel = mapViewModel)
            }
        }
    }
}
