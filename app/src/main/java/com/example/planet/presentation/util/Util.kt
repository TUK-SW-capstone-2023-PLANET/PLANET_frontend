package com.example.planet.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.concurrent.TimeUnit
import kotlin.math.round

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

// 이미지 파일 만들기
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,          /* prefix */
        ".jpg",          /* suffix */
        externalCacheDir        /* directory */
    )
    return image
}

fun getImageUri(context: Context): Uri {
    val file = context.createImageFile()
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
}

@Suppress("DEPRECATION", "NewApi")
fun Uri.parseBitmap(context: Context): Bitmap {
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
        true -> {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }

        else -> {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }
}

fun File.allDelete(): Boolean? {
    var returnData = false
    val result = runCatching {
        if (this.exists()) {
            val files = this.listFiles()
            if (files != null && files.size > 0) {
                files.forEachIndexed { index, file ->
                    returnData = file.delete()
                }
                return returnData
            } else {
                return false
            }
        } else {
            return false
        }
    }.onSuccess { return true }.onFailure { error ->
        Log.d(TAG, "error: ${error.message}")
        return false
    }

    return result.getOrNull()
}

fun Int.numberComma(): String {
    val decimal = DecimalFormat("#,###")
    return decimal.format(this)
}

fun Double.round(): String {
    val formatDistance = round(this * 100) / 100
    return formatDistance.toString()
}

fun Double.roundToDouble(): Double = round(this * 100) / 100


fun Long.formatTime(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    val formatter = String.format("%02d : %02d", minutes, seconds)
    return formatter
}

fun Long.secondsFormatTime(): String {
    val milliseconds = this * 1000
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    val formatter = String.format("%02d : %02d", minutes, seconds)
    return formatter
}

fun Double.paceFormat(): String {
    val minutes = this.toInt()
    val seconds = ((this - minutes) * 100).toInt()
    return "$minutes\'$seconds\""
}

fun Long.toSecond(): Long = this / 1000



fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
//    return contentResolver.query(this, null, null, null, null)?.let {
//        if (it.moveToNext()) {
//            val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//            Log.d(TAG, "displayName: $displayName")
//            val requestBody = object : RequestBody() {
//                override fun contentType(): MediaType? {
//                    return contentResolver.getType(this@asMultipart)?.toMediaType()
//                }
//
//                override fun writeTo(sink: BufferedSink) {
//                    sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
//                }
//            }
//            it.close()
//            MultipartBody.Part.createFormData(name, displayName, requestBody)
//        } else {
//            it.close()
//            null
//        }
//    }
    return contentResolver.query(this, null, null, null, null)?.use {
        if (it.moveToNext()) {
            val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            Log.d(TAG, "displayName: $displayName")
            val requestBody = object : RequestBody() {
                override fun contentType(): MediaType? {
                    return contentResolver.getType(this@asMultipart)?.toMediaType()
                }

                override fun writeTo(sink: BufferedSink) {
                    sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                }
            }
            MultipartBody.Part.createFormData(name, displayName, requestBody)
        } else {
            null
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission() {
    val MY_PERMISSION_LIST = listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
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