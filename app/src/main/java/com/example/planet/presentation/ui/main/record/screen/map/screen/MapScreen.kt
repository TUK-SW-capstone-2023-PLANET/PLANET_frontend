package com.example.planet.presentation.ui.main.record.screen.map.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.component.map.map.NaverMapClustering
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.presentation.ui.main.record.screen.map.component.RecordMapTab
import com.example.planet.presentation.ui.main.record.screen.map.component.RecordMapTextField
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CircleOverlay
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    trashCans: List<TrashCanItem>,
    hotPlaces: List<TrashCanItem>,
    startSearchActivity: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 18.0,
                minZoom = 7.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            )
        )
    }


    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = true,
                isScrollGesturesEnabled = true,
                isRotateGesturesEnabled = true,
                isZoomGesturesEnabled = true,
                isZoomControlEnabled = false,
                isTiltGesturesEnabled = false,
                isIndoorLevelPickerEnabled = false,
                isScaleBarEnabled = false,
                isCompassEnabled = false
            )
        )
    }
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
    val cameraPositionState = rememberCameraPositionState()

    var tabSelected by remember {
        mutableIntStateOf(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
        ) {
            if (tabSelected == 1) {
                hotPlaces.forEach {
                    // TODO: 나중에 리스트 타입 수정할 것
                    CircleOverlay(
                        center = LatLng(
                            cameraPositionState.position.target.latitude,
                            cameraPositionState.position.target.longitude
                        ), radius = 100.0,
                        color = colorResource(R.color.main_color2).copy(alpha = 0.7f)

                    )
                }
            } else {
                NaverMapClustering(items = trashCans)
            }


        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            RecordMapTextField(
                modifier = Modifier,
                placeholder = "주소, 장소 검색",
                icon = Icons.Outlined.Search
            ) {
                startSearchActivity()
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row {
                RecordMapTab(
                    modifier = Modifier.padding(end = 8.dp),
                    selected = { tabSelected == 0 },
                    icon = Icons.Outlined.LocalFireDepartment,
                    text = "핫 플레이스"
                ) {
                    tabSelected = 0
                }
                RecordMapTab(
                    modifier = Modifier,
                    selected = { tabSelected == 1 },
                    icon = Icons.Outlined.Delete,
                    text = "쓰레기통"
                ) {
                    tabSelected = 1
                }
            }
        }
    }
}

