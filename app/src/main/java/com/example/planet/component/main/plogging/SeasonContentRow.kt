package com.example.planet.component.main.plogging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R

@Composable
fun SeasonContentRow(
    medal: @Composable () -> Unit,
    rank: Int,
    tier: String,
    nickname: String,
    score: String,
    universityLogo: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        medal()
        Text(
            text = rank.toString() + "등",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.15f)
        )
        Box(modifier = Modifier.fillMaxWidth(0.15f).fillMaxHeight()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tier)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.6f).align(Alignment.CenterStart)
            )
        }

        Text(
            text = nickname,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        Text(
            text = "${score}점",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(universityLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.6f).align(Alignment.CenterStart)
            )
        }
    }
    Divider(
        thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3)
    )
}