package com.example.planet.presentation.ui.main.plogging.screen.ranking.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.component.common.TripleArrowIcon
import com.example.planet.component.main.SubTitle
import com.example.planet.component.main.SubTitleDescription

@Composable
fun MiddleHead(image: Painter, title: String, description: String, icon: @Composable () -> Unit = {}) {
    Divider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 16.dp),
        color = colorResource(id = R.color.font_background_color3)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Row {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 3.dp)
                    .size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                SubTitle(title = title)
                SubTitleDescription(description = description)
            }
            icon()
        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}