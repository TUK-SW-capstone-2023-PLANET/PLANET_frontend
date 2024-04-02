package com.example.planet.component.main.plogging

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun SeasonTitleRow() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "순위",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = "티어",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = "이름",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "점수",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "소속",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}