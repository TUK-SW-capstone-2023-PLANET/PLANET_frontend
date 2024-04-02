package com.example.planet.component.main.plogging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.util.round

@Composable
fun UniversityContentRow(
    medal: @Composable () -> Unit,
    rank: Int,
    nickname: String,
    score: String,
    contribution: Double,
    color: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color)
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            medal()
            Text(
                text = rank.toString(),
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.3f)
            )
            Text(
                text = nickname,
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier
            .weight(1f)
//            .height(IntrinsicSize.Min),
//            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${score}점",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${contribution.round()}%",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}