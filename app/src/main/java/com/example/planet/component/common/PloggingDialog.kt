package com.example.planet.component.common

import androidx.collection.SparseArrayCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.R
import com.example.planet.viewmodel.MapViewModel

@Composable
fun PloggingDialog(mapViewModel: MapViewModel = viewModel()) {
    Dialog(onDismissRequest = {
        mapViewModel.displayOnDialog()
        mapViewModel.startTimer()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            DialogContent(replay = { mapViewModel.startTimer() }) { mapViewModel.displayOnDialog() }
        }
    }
}

@Composable
fun DialogContent(stop: () -> Unit = {}, replay: () -> Unit, closeOnDialog: () -> Unit) {
    Column(
        modifier = Modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "플로깅 활동을 잠시 정지했습니다.", fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.font_background_color3))
                        .clickable { stop() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Text(text = "그만할래", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            }
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.main_color2))
                        .clickable {
                            closeOnDialog()
                            replay()
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Text(text = "다시할래", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}