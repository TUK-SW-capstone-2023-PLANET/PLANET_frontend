package com.example.planet.component.main.plogging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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

@Composable
fun SeasonContentRow(
    medal: @Composable () -> Unit,
    rank: Int,
    tier: String,
    nickname: String,
    score: String,
) {
//    Row(
//        Modifier
//            .height(IntrinsicSize.Min) //intrinsic measurements
//            .fillMaxWidth()
//            .background(Color.Yellow)
//    ) {
//        Text("First Text")
//
//        Divider(
//            color = Color.Red,
//            modifier = Modifier
//                .fillMaxHeight()  //fill the max height
//                .width(1.dp)
//        )
//
//        Text("Second text")
//    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        medal()
        Text(
            text = rank.toString() + "등",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.15f)
        )
        Text(
            text = "티어",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(0.15f)
        )
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
        Text(
            text = "소속",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
    Divider(
        thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3)
    )
}