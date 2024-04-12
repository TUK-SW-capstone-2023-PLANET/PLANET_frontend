package com.example.planet.presentation.ui.main.plogging.screen.ranking.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun UniversityIndividualGraph(
    visible: Boolean = false,
    score: String,
    graphHeight: Dp,
    colors: List<Color>,
    userName: String,
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = score,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 9.sp
            )
        }
        AnimatedVisibility(visible = visible,
            enter = slideInVertically { it }) {
            Canvas(
                modifier = Modifier
                    .height(graphHeight)
                    .width(40.dp)
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
            visible = visible,
            enter = scaleIn()
        ) {
            Text(
                text = userName,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 10.sp
            )
        }
    }
}