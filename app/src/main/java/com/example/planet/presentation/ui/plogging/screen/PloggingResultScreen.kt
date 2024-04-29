package com.example.planet.presentation.ui.plogging.screen

import android.graphics.PointF
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.planet.presentation.ui.plogging.component.DateCard
import com.example.planet.presentation.ui.plogging.component.PloggingResultTopAppBar
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Preview(showBackground = true)
@Composable
fun PloggingResultScreen() {
    val cameraPositionState = rememberCameraPositionState()

    cameraPositionState.position.target // 카메라의 현재 위치
    Column(modifier = Modifier.fillMaxSize()) {
        PloggingResultTopAppBar()
        DateCard()
        NaverMap(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.3f)
                .pointerInput(Unit) {// 뷰 페이저가 지도의 포커싱을 가져가는 issue를 해결
                    detectDragGestures { _, dragAmount ->
                        cameraPositionState.stop()
                    }
                },
            cameraPositionState = cameraPositionState
        )
    }
}