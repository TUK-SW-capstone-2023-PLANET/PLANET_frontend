package com.example.planet.component.main.plogging.ranking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R

@Composable
fun TearProfile(imageUrl: String, imageSize: Dp, userName: String, userScore: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userName, color = colorResource(id = R.color.font_background_color1), fontSize = 10.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$userScore 점", color = colorResource(id = R.color.font_background_color1), fontSize = 9.sp)
        }
    }
}