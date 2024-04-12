package com.example.planet.presentation.ui.main.plogging.ranking.component

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
fun UniversityIndividualTitleRow() {
    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = colorResource(id = R.color.font_background_color3))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(
                text = "순위",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.3f)
            )
            Text(
                text = "이름",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = "점수",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "기여도",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth(), color = colorResource(id = R.color.font_background_color3))
}

@Composable
fun UniversityIndividualContentRow(
    rank: Int,
    nickname: String,
    score: String,
    contribution: Double,
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
        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically
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