package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R

@Composable
fun HotPostingCard(
    modifier: Modifier = Modifier,
    image: String,
    name: String,
    date: String,
    title: String,
    content: String,
    heartCount: Int,
    commentCount: Int,
    viewCount: Int,
    onClick: () -> Unit
) {
    val nameTextStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    val dateTextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )
    val titleTextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    val contentTextStyle = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium)

    Column(modifier = modifier.fillMaxWidth().clickable { onClick() }.padding(start = 30.dp, end = 30.dp, top = 9.dp, bottom = 9.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 2.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape).size(27.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = name, style = nameTextStyle)
                Text(text = date, style = dateTextStyle)
            }
        }
        Column(
            modifier
                .padding(top = 8.dp, bottom = 5.dp)
        ) {
            Text(text = title, style = titleTextStyle)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = content, style = contentTextStyle)
        }

        PostingCardIcons(modifier = Modifier, heartCount = heartCount, commentCount = commentCount, viewCount = viewCount)
    }

    HorizontalDivider(
        thickness = 1.dp,
        color = colorResource(id = R.color.font_background_color3),
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}