package com.example.planet.presentation.ui.main.plogging.screen.message.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun MessageLogCard(type: String, date: String, content: String) {
    val typeStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (type == "받은 쪽지") colorResource(id = R.color.red) else colorResource(id = R.color.main_color1)
    )

    val dateStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        color = colorResource(id = R.color.font_background_color3)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
        .padding(start = 20.dp, end = 20.dp, top = 7.dp, bottom = 11.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(text = type, style = typeStyle, modifier = Modifier.padding(end = 6.dp))
                Text(text = date, style = dateStyle)
            }
            Text(text = content, fontSize = 12.sp,)
        }
        Image(
            painter = painterResource(id = R.drawable.temporary_user_icon),
            contentDescription = null,
            modifier = Modifier.size(17.dp)
        )
    }
}