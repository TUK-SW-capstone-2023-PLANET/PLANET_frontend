package com.example.planet.presentation.ui.plogging.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.plogging.screen.TrashItem

@Composable
fun ResultGraphBoard(trashList: List<TrashItem>) {

    val leftLineTrashes = trashList.subList(0, 5)
    val rightLineTrashes = trashList.subList(5, 10)
    val scoreColor = colorResource(id = R.color.main_color1)
    val countColor = colorResource(id = R.color.main_color2)

    GraphTitle(text = "내가 모은 쓰레기")

    GraphDescription(
        color1 = scoreColor,
        text1 = "전체 개수 비율",
        color2 = countColor,
        text2 = "전체 점수 비율"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            leftLineTrashes.forEach {
                TrashGraphCard(
                    trash = it,
                    highestScore = leftLineTrashes[0].score,
                    scoreColor = scoreColor,
                    countColor = countColor
                )
            }
        }
        VerticalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color4),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            rightLineTrashes.forEach {
                TrashGraphCard(
                    trash = it,
                    highestScore = leftLineTrashes[0].score,
                    scoreColor = scoreColor,
                    countColor = countColor
                )
            }
        }
    }
}

@Composable
fun GraphTitle(text: String) {
    val textStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color1),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun GraphDescription(color1: Color, text1: String, color2: Color, text2: String) {
    val textMeasurer1 = rememberTextMeasurer()
    val style1 = TextStyle(
        fontSize = 11.sp,
        color = colorResource(id = R.color.font_background_color2),
    )
    val textLayoutResult1 = remember(text1, style1) {
        textMeasurer1.measure(text1, style1)
    }
    val textMeasurer2 = rememberTextMeasurer()
    val style2 = TextStyle(
        fontSize = 11.sp,
        color = colorResource(id = R.color.font_background_color2),
    )
    val textLayoutResult2 = remember(text2, style2) {
        textMeasurer2.measure(text2, style2)
    }

    val circleSize = 12f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(horizontal = 30.dp)
    ) {
        Canvas(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight()
        ) {
            drawCircle(
                color = color1,
                radius = 12f,
                center = Offset(0f + circleSize, this.size.center.y)
            )
            drawText(
                textMeasurer = textMeasurer1,
                text = text1,
                style = style1,
                topLeft = Offset(
                    x = circleSize * 2f + 10,
                    y = center.y - textLayoutResult1.size.height / 2,
                )
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Canvas(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight()
        ) {
            drawCircle(
                color = color2,
                radius = 12f,
                center = Offset(0f + circleSize, this.size.center.y)
            )
            drawText(
                textMeasurer = textMeasurer2,
                text = text2,
                style = style2,
                topLeft = Offset(
                    x = circleSize * 2f + 10,
                    y = center.y - textLayoutResult2.size.height / 2,
                )
            )
        }
        Box(modifier = Modifier.weight(0.5f))
    }
}

@Composable
fun TrashGraphCard(trash: TrashItem, highestScore: Int, scoreColor: Color, countColor: Color) {

    val trashTextStyle =
        TextStyle(
            color = colorResource(id = R.color.font_background_color1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = trash.name, modifier = Modifier.padding(end = 20.dp), style = trashTextStyle)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            ) {
                drawRect(
                    color = countColor,
                    size = Size(this.size.width / highestScore * trash.score, size.height)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            ) {
                drawRect(color = scoreColor, size = this.size)
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}