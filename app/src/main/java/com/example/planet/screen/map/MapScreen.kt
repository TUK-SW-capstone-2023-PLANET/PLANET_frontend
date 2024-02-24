package com.example.planet.screen.map

import android.graphics.PointF
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.TAG
import com.example.planet.component.map.common.CameraButton
import com.example.planet.component.map.map.MyLocationButton
import com.example.planet.component.map.map.NaverMapClustering
import com.example.planet.viewmodel.MapViewModel
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
    mapViewModel: MapViewModel = viewModel(),
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
) {
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
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
    val cameraPositionState = rememberCameraPositionState()
    var currentUserLocation: LatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    val cameraPosition =
        CameraPosition(LatLng(currentUserLocation.latitude, currentUserLocation.longitude), 14.0)
    cameraPositionState.position.target // 카메라의 현재 위치

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            modifier = Modifier.pointerInput(Unit) {// 뷰 페이저가 지도의 포커싱을 가져가는 issue를 해결
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
                currentUserLocation = LatLng(location.latitude, location.longitude)
                Log.d(TAG, "currentUserLocation: $currentUserLocation")
            }
        ) {

            NaverMapClustering(items = mapViewModel.trashCanItem)
            PathOverlay(coords = list)  // 내가 해왔던 경로 찍으면 됌
        }
        // 여기서부터 임시로 거리측정에 오류가 있는지 없는지 확인하기 위함
        Card(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .align(Alignment.BottomCenter)
                .padding(16.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "${mapViewModel.distance.value}", modifier = Modifier.padding(16.dp))
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
            cameraLauncher.launch(mapViewModel.uri.value)
        }
    }
}