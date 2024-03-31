package com.example.planet.component.main.plogging.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun TrophyProfile(image: Painter, imageSize: Dp, userIcon: Painter, userName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = userIcon,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userName,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 8.sp
            )
        }
    }
}