package com.example.planet.presentation.ui.main.plogging.screen.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R

@Composable
fun UniversityTitleRow() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 2.dp, bottom = 2.dp)
    ) {

        Text(
            text = "순위",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "학교",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "이름",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = "점수",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}

@Composable
fun UniversityContentRow(
    rank: Int,
    universityLogo: String,
    universityName: String,
    score: String,
    color: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = color)
            .padding(vertical = 2.dp)
    ) {
        when (rank) {
            1 -> {
                Divider(
                    color = colorResource(id = R.color.ranking_color1),
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            2 -> {
                Divider(
                    color = colorResource(id = R.color.ranking_color2),
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            3 -> {
                Divider(
                    color = colorResource(id = R.color.ranking_color3),
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            else -> {
                Spacer(modifier = Modifier.width(24.dp))
            }
        }

        Text(
            text = rank.toString(),
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.08f)
        )
        Box(modifier = Modifier.weight(0.12f)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(universityLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .align(Alignment.CenterStart)
            )
        }
        Text(
            text = universityName,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = "$score 점",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}