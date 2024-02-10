package com.example.myapplication.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                Map()

                Test()
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun Test() {
        var changed by remember {
            mutableStateOf(false)
        }
        Log.d("daeYoung", "changed: $changed")

        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(3.0f),
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(width = 1.dp, color = Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E8E8))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .clickable { changed = false },
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = if (changed) Color(0xFFE8E8E8) else Color(0xFFFFFFFF))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "플로깅", modifier = Modifier.align(Alignment.Center))
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { changed = true },
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = if (changed) Color(0xFFFFFFFF) else Color(0xFFE8E8E8))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "기록", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalNaverMapApi::class)
    @Composable
    fun Map() {
        var mapProperties by remember {
            mutableStateOf(
                MapProperties(maxZoom = 10.0, minZoom = 5.0)
            )
        }
        var mapUiSettings by remember {
            mutableStateOf(
                MapUiSettings(isLocationButtonEnabled = true)
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(properties = mapProperties, uiSettings = mapUiSettings) {
//            Column {
//                Button(onClick = {
//                    mapProperties = mapProperties.copy(
//                        isBuildingLayerGroupEnabled = !mapProperties.isBuildingLayerGroupEnabled
//                    )
//                }) {
//                    Text(text = "Toggle isBuildingLayerGroupEnabled")
//                }
//                Button(onClick = {
//                    mapUiSettings = mapUiSettings.copy(
//                        isLocationButtonEnabled = !mapUiSettings.isLocationButtonEnabled
//                    )
//                }) {
//                    Text(text = "Toggle isLocationButtonEnabled")
//                }
//            }
            }
        }
    }
}