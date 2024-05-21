package com.example.planet.presentation.ui.main.plogging.screen.user.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
fun MyReportCard(
    type: String,
    title: String,
    content: String,
    name: String,
    report: Long,
    onClick: () -> Unit
) {
    val typeStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (type == "게시글") colorResource(id = R.color.red) else colorResource(id = R.color.blue)
    )
    val titleStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    val contentStyle = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    val reportStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )

    HorizontalDivider(
        thickness = 1.dp,
        color = colorResource(id = R.color.font_background_color3),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    )
    Row(modifier = Modifier.fillMaxSize().clickable { onClick() }.padding(start = 19.dp, end = 19.dp, top = 14.dp, bottom = 10.dp), verticalAlignment = Alignment.Bottom) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "$type 신고 내역", style = typeStyle, modifier = Modifier.padding(bottom = 4.dp))
            Text(text = "글 제목: $title", style = titleStyle, modifier = Modifier.padding(bottom = 4.dp))
            Text(text = "$type 내용: $content", style = contentStyle, modifier = Modifier.padding(bottom = 5.dp))
            Text(text = "작성자: $name", style = contentStyle)
        }
        Column {
            Text(text = "신고 접수 번호", style = reportStyle, modifier = Modifier.padding(bottom = 1.dp))
            Text(text = report.toString(), style = reportStyle, modifier = Modifier.padding(bottom = 1.dp))
        }
    }

}