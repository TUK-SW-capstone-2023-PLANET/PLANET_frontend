package com.example.planet.presentation.ui.main.plogging.screen.message.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun CharCard(name: String, lastMessage: String, date: String, image: String, onClick: () -> Unit) {
    val dateStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        color = colorResource(id = R.color.font_background_color2)
    )
    val nameStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    )

    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        color = colorResource(id = R.color.font_background_color3)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = 29.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.temporary_user_icon),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(end = 11.dp)
                    .size(49.dp)

            )
            Column {
                Text(text = name, style = nameStyle)
                Text(text = lastMessage, fontSize = 10.sp)
            }
        }
        Row {
            Text(text = date, style = dateStyle)
        }
    }

}