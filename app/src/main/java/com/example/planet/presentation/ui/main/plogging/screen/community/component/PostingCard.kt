package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun PostingCard(
    title: String,
    content: String,
    data: String,
    name: String,
    heartCount: Int,
    commentCount: Int,
    viewCount: Int,
    onClick: () -> Unit
) {
    val titleStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    val contentStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
    val timeStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = 19.dp, end = 19.dp, top = 14.dp, bottom = 10.dp)
    ) {
        Text(text = title, style = titleStyle, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = content, style = contentStyle, modifier = Modifier.padding(bottom = 5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Text(text = "$data | ", style = timeStyle)
                Text(text = name, style = timeStyle)
            }
            Row {
                PostingCardIcons(
                    heartCount = heartCount,
                    commentCount = commentCount,
                    viewCount = viewCount
                )
            }
        }
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = colorResource(id = R.color.font_background_color3),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun PostingCardIcons(modifier: Modifier = Modifier, heartCount: Int, commentCount: Int, viewCount: Int) {
    val textStyle = TextStyle(
        fontSize = 10.sp,
        color = colorResource(id = R.color.font_background_color2)
    )
    Row(modifier = modifier) {
        Row(modifier = Modifier.padding(end = 5.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.heartborder_icon),
                contentDescription = null,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = heartCount.toString(),
                style = textStyle,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
        Row(modifier = Modifier.padding(end = 5.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.commentborder_icon),
                contentDescription = null,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = commentCount.toString(),
                style = textStyle,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.personborder_icon),
                contentDescription = null,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = viewCount.toString(),
                style = textStyle,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}