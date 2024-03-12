package com.example.planet.component.main.plogging

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun UniversityTitleRow() {
    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp), color = colorResource(id = R.color.font_background_color3))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
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
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = "기여도",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3))
}