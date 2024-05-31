package com.example.planet.presentation.ui.main.record.screen.record.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDayOfWeek() {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val dayOfWeek = arrayOf("일", "월", "화", "수", "목", "금", "토")
        repeat(7) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
            ) {
                Text(
                    text = dayOfWeek[it],
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }
}