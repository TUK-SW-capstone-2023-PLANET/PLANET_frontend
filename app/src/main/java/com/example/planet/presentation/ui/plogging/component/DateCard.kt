package com.example.planet.presentation.ui.plogging.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import java.time.YearMonth

@Composable
fun DateCard(yearMonth: String, hourMinutes: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 15.dp, bottom = 9.dp)
    ) {
        Text(
            text = yearMonth,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = colorResource(id = R.color.font_background_color1)
        )
        Text(
            text = hourMinutes,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.font_background_color2)
        )
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth().shadow(1.dp), color = colorResource(id = R.color.font_background_color3))
}