package com.example.planet.presentation.ui.main.plogging.screen.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
fun UniversityGraph(
    visible: () -> Boolean = { false },
    universityLogo: String,
    score: String,
    graphHeight: Dp,
    colors: List<Color>,
    universityName: String,
    medal: Painter
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible(),
            enter = scaleIn()
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(universityLogo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = score,
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 9.sp
                )
            }
        }
        AnimatedVisibility(visible = visible(),
            enter = slideInVertically { it }) {
            Canvas(
                modifier = Modifier
                    .height(graphHeight)
                    .width(50.dp)
                    .animateContentSize()
            ) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = colors
                    )
                )
            }
        }
        AnimatedVisibility(
            visible = visible(),
            enter = scaleIn()
        ) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = universityName,
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 12.sp
                )
                Image(
                    painter = medal,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}