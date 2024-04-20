package com.example.planet.component.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.R
import com.example.planet.presentation.viewmodel.MainViewModel

@Composable
fun MainTopSwitch(mainViewModel: MainViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.font_background_color3),
            contentColor = colorResource(id = R.color.font_background_color1)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            PloggingSwitchButton(
                text = "플로깅",
                modifier = Modifier.align(Alignment.CenterStart).clickable { mainViewModel.changeRecordScreen() },
                enabled = mainViewModel.ploggingOrRecordSwitch.value
            )
            RecordSwitchButton(
                text = "기록",
                modifier = Modifier.align(Alignment.CenterEnd).clickable { mainViewModel.changePloggingScreen() },
                enabled = mainViewModel.ploggingOrRecordSwitch.value
            )
        }
    }
}

@Composable
fun PloggingSwitchButton(modifier: Modifier = Modifier, text: String, enabled: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.48f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) Color.White else colorResource(id = R.color.font_background_color3),
            contentColor = if (enabled) colorResource(id = R.color.font_background_color1) else colorResource(
                id = R.color.font_background_color2
            )
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
    }
}

@Composable
fun RecordSwitchButton(modifier: Modifier = Modifier, text: String, enabled: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.48f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!enabled) Color.White else colorResource(id = R.color.font_background_color3),
            contentColor = if (!enabled) colorResource(id = R.color.font_background_color1) else colorResource(
                id = R.color.font_background_color2
            )
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
    }
}