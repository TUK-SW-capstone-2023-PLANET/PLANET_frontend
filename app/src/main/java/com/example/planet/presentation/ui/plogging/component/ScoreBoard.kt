package com.example.planet.presentation.ui.plogging.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun ScoreCard(modifier: Modifier = Modifier, text: String, unit: String) {
    val semiBoldStyle =
        TextStyle(
            color = colorResource(id = R.color.font_background_color1),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    val mediumStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color2),
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, style = semiBoldStyle)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = unit, style = mediumStyle)
    }
}

@Composable
fun ScoreBoard(time: String, distance: String, kcal: String, pace: String, totalTrashCount: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp, bottom = 15.dp)
    ) {
        ScoreCard(modifier = Modifier.weight(1f), text = time, unit = "시간")
        ScoreCard(modifier = Modifier.weight(1f), text = distance, unit = "거리(km)")
        ScoreCard(modifier = Modifier.weight(1f), text = "$kcal kcal", unit = "소모 칼로리")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 22.dp)
    ) {
        ScoreCard(modifier = Modifier.weight(1f), text = pace, unit = "평균 페이스")
        ScoreCard(modifier = Modifier.weight(1f), text = "$totalTrashCount 개", unit = "주운 쓰레기")
        ScoreCard(modifier = Modifier.weight(1f), text = "$score 점", unit = "점수")
    }
}