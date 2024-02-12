package com.example.myapplication.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.network.GeocoderApi
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.util.noRippleClickable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var geocoderApi: GeocoderApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Map()
//                ScrollToTopDerivedAndRememberedCase()
//                Test()
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
                        .noRippleClickable { changed = false },
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = if (changed) Color(0xFFE8E8E8) else Color(
                            0xFFFFFFFF
                        )
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "플로깅", modifier = Modifier.align(Alignment.Center))
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .noRippleClickable { changed = true },
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = if (changed) Color(0xFFFFFFFF) else Color(
                            0xFFE8E8E8
                        )
                    )
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
        val coroutineScope = rememberCoroutineScope()

        val list = listOf(
            LatLng(37.5660645, 126.9826732),
            LatLng(37.5660294, 126.9826723),
            LatLng(37.5658526, 126.9826611),
            LatLng(37.5658040, 126.9826580),
            LatLng(37.5657697, 126.9826560),
            LatLng(37.5654413, 126.9825880),
            LatLng(37.5652157, 126.9825273),
            LatLng(37.5650560, 126.9824843),
            LatLng(37.5647789, 126.9824114),
            LatLng(37.5646788, 126.9823861),
            LatLng(37.5644062, 126.9822963),
            LatLng(37.5642519, 126.9822566),
            LatLng(37.5641517, 126.9822312),
            LatLng(37.5639965, 126.9821915),
            LatLng(37.5636536, 126.9820920),
            LatLng(37.5634424, 126.9820244),
            LatLng(37.5633241, 126.9819890),
            LatLng(37.5632772, 126.9819712),
            LatLng(37.5629404, 126.9818433),
            LatLng(37.5627733, 126.9817584),
            LatLng(37.5626694, 126.9816980),
            LatLng(37.5624588, 126.9815738),
            LatLng(37.5620376, 126.9813140),
            LatLng(37.5619426, 126.9812252),
            LatLng(37.5613227, 126.9814831),
            LatLng(37.5611995, 126.9815372),
            LatLng(37.5609414, 126.9816749),
            LatLng(37.5606785, 126.9817390),
            LatLng(37.5605659, 126.9817499),
            LatLng(37.5604892, 126.9817459),
            LatLng(37.5604540, 126.9817360),
            LatLng(37.5603484, 126.9816993),
            LatLng(37.5602092, 126.9816097),
            LatLng(37.5600048, 126.9814390),
            LatLng(37.5599702, 126.9813612),
            LatLng(37.5599401, 126.9812923),
            LatLng(37.5597114, 126.9807346),
            LatLng(37.5596905, 126.9806826),
            LatLng(37.5596467, 126.9805663),
            LatLng(37.5595203, 126.9801199),
            LatLng(37.5594901, 126.9800149),
            LatLng(37.5594544, 126.9798883),
            LatLng(37.5594186, 126.9797436),
            LatLng(37.5593948, 126.9796634),
            LatLng(37.5593132, 126.9793526),
            LatLng(37.5592831, 126.9792622),
            LatLng(37.5590904, 126.9788854),
            LatLng(37.5589081, 126.9786365),
            LatLng(37.5587088, 126.9784125),
            LatLng(37.5586699, 126.9783698),
        )

        var mapProperties by remember {
            mutableStateOf(
                MapProperties(
                    maxZoom = 18.0,
                    minZoom = 7.0,
                    locationTrackingMode = LocationTrackingMode.Face,
                )
            )
        }
        var mapUiSettings by remember {
            mutableStateOf(
                MapUiSettings(isLocationButtonEnabled = true)
            )
        }
        val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
        val cameraPositionState = rememberCameraPositionState()

        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(
                locationSource = locationSource,
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = mapUiSettings
            ) {
                PathOverlay(coords = list)
            }
            Button(
                onClick = {
                    Log.d("daeYoung", "${cameraPositionState.position}")
                    coroutineScope.launch {
                        val latLng = cameraPositionState.position.target
                        val result = geocoderApi.getMonthWaterBill(latLng)
                        Log.d("daeYoung", "result: ${result.results}")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Text(text = "reverse geocode")
            }
        }
    }
}

@Composable
fun ScrollToTopDerivedAndRememberedCase(lazyListState: LazyListState = rememberLazyListState()) {
    val isEnabledDerivedStateCase by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }
//    val isEnabledRememberCase = remember(lazyListState.firstVisibleItemIndex) { lazyListState.firstVisibleItemIndex > 0}
    val isEnabledRememberCase = remember { mutableStateOf(lazyListState.firstVisibleItemIndex > 0) }

    Log.d(
        "daeYoung",
        "isEnabledDerivedStateCase: $isEnabledDerivedStateCase\nisEnabledRememberCase: ${isEnabledRememberCase.value}"
    )
//    Log.d("daeYoung", "isEnabledDerivedStateCase: $isEnabledDerivedStateCase")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
            items(50) {
                Text(text = "Text $it")
            }
        }
        Button(onClick = { /*TODO*/ }, enabled = isEnabledDerivedStateCase) {
            Text(text = "Derived State Button")
        }

        Button(onClick = { /*TODO*/ }, enabled = isEnabledRememberCase.value) {
            Text(text = "Remembered Button")
        }
    }
}