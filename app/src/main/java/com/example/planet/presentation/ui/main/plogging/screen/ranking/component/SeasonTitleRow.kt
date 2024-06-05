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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R

@Composable
fun SeasonTitleRow() {
    HorizontalDivider(
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
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = "티어",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = "이름",
            color = MaterialTheme.colorScheme.onSecondary,
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
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "소속",
                color = MaterialTheme.colorScheme.onSecondary,
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

@Composable
fun SeasonContentRow(
    rank: Int,
    tier: String,
    nickname: String,
    score: String,
    universityLogo: String,
    color: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
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
                4 -> {
                    Divider(
                        color = colorResource(id = R.color.main_color2).copy(alpha = 0.8f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(4.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
                5 -> {
                    Divider(
                        color = colorResource(id = R.color.main_color2).copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(4.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
                6 -> {
                    Divider(
                        color = Color(0xFFBED7EE).copy(alpha = 0.4f),
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
                text = rank.toString() + "등",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tier)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.8f)
            )
        }
        Text(
            text = nickname,
            color = MaterialTheme.colorScheme.onSecondary,
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
                text = "${score}점",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(universityLogo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}