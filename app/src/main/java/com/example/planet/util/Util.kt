package com.example.planet.util

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.content.FileProvider
import com.example.planet.BuildConfig
import com.example.planet.TAG
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File
import java.util.Objects

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

// 새 파일을 만들기 위한 함수
fun Context.createNewFile() = File(
    filesDir, "${System.currentTimeMillis()}.jpg"
).apply { createNewFile() }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission() {
    val MY_PERMISSION_LIST = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 권한 부여
            cameraPermissionState.launchPermissionRequest()
            Log.d(TAG, "camera permission is ok")
        } else {
            // 권한 거부 처리, 처음 거절했을 때
            Log.d(TAG, "camera permission is reject")
        }
    }
    LaunchedEffect(cameraPermissionState) {
        if (cameraPermissionState.status.isGranted.not() && cameraPermissionState.status.shouldShowRationale) {
            // 필요한 경우 근거 표시
            // cameraPermissionState.status.shouldShowRationale 는 카메라 권한을 이미 거절했을 때 로그가 남음
            // 즉, 거절한 뒤 다시 앱을 실행했을 때 로그가 찍힘
            Log.d(TAG, "camera permission is reject(LaunchedEffect)")
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            Log.d(TAG, "camera permission is ok(LaunchedEffect)")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    cameraPermissionState.launchPermissionRequest()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TakePicture(context: Context) {
    val file = context.createNewFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        capturedImageUri = uri
    }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
}