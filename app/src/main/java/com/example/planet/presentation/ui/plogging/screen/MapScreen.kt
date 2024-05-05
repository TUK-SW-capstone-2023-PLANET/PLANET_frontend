package com.example.planet.presentation.ui.plogging.screen

import android.graphics.PointF
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.TAG
import com.example.planet.component.map.common.CameraButton
import com.example.planet.component.map.map.MyLocationButton
import com.example.planet.component.map.map.NaverMapClustering
import com.example.planet.presentation.viewmodel.PloggingViewModel
import com.example.planet.presentation.util.getImageUri
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    ploggingViewModel: PloggingViewModel = viewModel(),
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
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
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
    val cameraPositionState = rememberCameraPositionState()

    val cameraPosition =
        CameraPosition(LatLng(ploggingViewModel.currentLatLng?.latitude ?: 0.0, ploggingViewModel.currentLatLng?.longitude ?: 0.0), 14.0)
    cameraPositionState.position.target // 카메라의 현재 위치

    DisposableEffect(Unit) {
        onDispose {
            val result = ploggingViewModel.cashFileAllDelete()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(modifier = Modifier.pointerInput(Unit) {// 뷰 페이저가 지도의 포커싱을 가져가는 issue를 해결
            detectDragGestures { _, dragAmount ->
                cameraPositionState.move(
                    CameraUpdate.scrollBy(
                        PointF(dragAmount.x, dragAmount.y)
                    )
                )
            }
        },
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            // 현재유저의 LatLng 찾는 콜백함수
            onLocationChange = { location ->
//                if (mapViewModel.currentLatLng != null) {
//                    if (mapViewModel.pastLatLng == null) {
//                        mapViewModel.pastLatLng = mapViewModel.currentLatLng
//                    }
//                    else if (mapViewModel.currentLatLng!!.latitude != mapViewModel.pastLatLng!!.latitude || mapViewModel.currentLatLng!!.longitude != mapViewModel.pastLatLng!!.longitude) {
//                        mapViewModel.pastLatLng = mapViewModel.currentLatLng
//                    }
//                }
//                        Log.d(TAG, "${DistanceManager.getDistance(37.638591, 127.026325, 37.643801, 127.031755).toString()}")
//                        Log.d(TAG, "${DistanceManager.getDistance(37.4021, 127.1108, 37.3944, 127.1108).toString()}")
                ploggingViewModel.currentLatLng = LatLng(location.latitude, location.longitude)
            }) {

            NaverMapClustering(items = ploggingViewModel.trashCanItem)
            // PathOverlay(coords = list)  // 내가 해왔던 경로 찍으면 됌
        }
        // 여기서부터 임시로 거리측정에 오류가 있는지 없는지 확인하기 위함
        Card(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .align(Alignment.BottomCenter)
                .padding(16.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Text(text = "거리: ${ploggingViewModel.distance.value}", modifier = Modifier.padding(16.dp))
                Text(text = "분속: ${ploggingViewModel.minSpeed.value}", modifier = Modifier.padding(horizontal = 16.dp))
                Text(text = "MET: ${ploggingViewModel.met.value}", modifier = Modifier.padding(16.dp))
                Text(text = "kacl: ${ploggingViewModel.kcal.value}", modifier = Modifier.padding(horizontal = 16.dp))
                Text(text = "pace: ${ploggingViewModel.pace.value}", modifier = Modifier.padding(16.dp))
            }
        }
        // 여기까지
        MyLocationButton(
            modifier = Modifier
                .padding(bottom = 46.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            coroutineScope.launch {
                cameraPositionState.animate(
                    CameraUpdate.toCameraPosition(cameraPosition),
                    animation = CameraAnimation.Fly,
                    durationMs = 3000
                )
            }
        }
        CameraButton(
            modifier = Modifier
                .padding(bottom = 112.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            cameraLauncher.launch(getImageUri(context))
        }
    }
}