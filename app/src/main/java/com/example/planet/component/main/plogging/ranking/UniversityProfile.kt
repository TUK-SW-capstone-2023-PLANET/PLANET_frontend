package com.example.planet.component.main.plogging.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R

@Composable
fun UniversityProfile(imageUrl: String, imageSize: Dp, medal: Painter, universityName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = universityName,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 9.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = medal,
                contentDescription = null,
                modifier = Modifier.size(11.dp)
            )
        }
    }

}