package com.example.planet.screen.map

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.BuildConfig
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.component.map.common.CameraButton
import com.example.planet.component.map.common.LockButton
import com.example.planet.component.map.record.RoundCornerCard
import com.example.planet.util.allDelete
import com.example.planet.util.createImageFile
import com.example.planet.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.Objects

@OptIn(ExperimentalPermissionsApi::class)
//@Preview(showBackground = true)
@Composable
fun RecordScreen(mapViewModel: MapViewModel = viewModel()) {
    val timerColorList =
        listOf(colorResource(id = R.color.main_color1), colorResource(id = R.color.main_color3))
    val fontColor = colorResource(id = R.color.font_background_color1)
    val textMeasurer1 = rememberTextMeasurer()
    val textMeasurer2 = rememberTextMeasurer()
    val hour = "${mapViewModel.hour.value}hour"
    val minuteSecond = mapViewModel.formattedTime.value
    val textStyle1 = TextStyle(color = fontColor, fontWeight = FontWeight.Medium)
    val textStyle2 = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 45.sp)
    val textLayoutResult1 = remember(hour) {
        textMeasurer1.measure(hour, textStyle1)
    }
    val textLayoutResult2 = remember(minuteSecond) {
        textMeasurer2.measure(minuteSecond, textStyle2)
    }

    DisposableEffect(Unit) {
        onDispose {
            val path = "/storage/emulated/0/Android/data/${BuildConfig.APPLICATION_ID}/cache/"
            val cashFile = File(path)
            val result = cashFile.allDelete()
            Log.d(TAG, "onDispose 실행되나?\n cashFile.delete(): ${result}")
        }
    }
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) capturedImageUri = uri
        }


    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.main_color4))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "열심히 하는 당신을 응원합니다.", color = colorResource(id = R.color.font_background_color1))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val circleSize = Size(width = size.width / 1.5f, height = size.height / 1.5f)
                drawArc(
                    color = Color.White,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(
                        size.width - size.width / 1.20f,
                        size.height - size.height / 1.20f
                    ),
                    size = circleSize,
                )
                drawArc(
                    brush = Brush.sweepGradient(timerColorList),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(
                        size.width - size.width / 1.20f,
                        size.height - size.height / 1.20f
                    ),
                    size = circleSize,
                    style = Stroke(width = 50.0f)
                )
                drawText(
                    textMeasurer = textMeasurer1,
                    text = hour,
                    style = textStyle1,
                    topLeft = Offset(
                        center.x - textLayoutResult1.size.width / 2,
                        center.y - circleSize.height / 3f
                    )
                )
                drawText(
                    textMeasurer = textMeasurer2,
                    text = minuteSecond,
                    style = textStyle2,
                    topLeft = Offset(
                        center.x - textLayoutResult2.size.width / 2,
                        center.y - textLayoutResult2.size.height / 1.8f
                    )
                )
            }
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp)
                    .size(50.dp)
                    .clickable {},
                tint = colorResource(id = R.color.font_background_color3)
            )
        }

        RoundCornerCard(
            modifier = Modifier.fillMaxWidth(),
            title = "이동 거리",
            contentColor = colorResource(id = R.color.main_color2)
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append("12.89")
                }
                withStyle(SpanStyle(fontSize = 25.sp, fontWeight = FontWeight.Medium)) {
                    append(" km")
                }
            })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            RoundCornerCard(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth(0.5f), title = "현재 페이스"
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("5")
                    }
                    withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold)) {
                        append("'")
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("31")
                    }
                    withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold)) {
                        append("\"")
                    }
                })
            }
            RoundCornerCard(modifier = Modifier, title = "칼로리") {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("320")
                    }
                    withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)) {
                        append(" kcal")
                    }
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            RoundCornerCard(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth(0.5f),
                title = "현재 점수"
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("3,260")
                    }
                    withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)) {
                        append("점")
                    }
                })
            }
            RoundCornerCard(modifier = Modifier, title = "주운 쓰레기") {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    ) {
                        append("12")
                    }
                    withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)) {
                        append(" 개")
                    }
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            LockButton()
            CameraButton {
                if (cameraPermissionState.status.isGranted) {
                    runCatching { cameraLauncher.launch(uri) }.onFailure { error ->
                        Log.d(TAG, "error: ${error.message}")
                    }

                } else {
                    cameraPermissionState.launchPermissionRequest()
                }
            }
        }
    }
}