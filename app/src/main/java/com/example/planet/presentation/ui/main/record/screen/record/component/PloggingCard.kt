package com.example.planet.presentation.ui.main.record.screen.record.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.MoreTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R

@Composable
fun PloggingCard(
    image: String,
    address: String,
    trashCount: Int,
    distance: String,
    ploggingTime: String,

    ) {
    val addressStyle = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    )
    val trashCountStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )
    val timeCountStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color3)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 11.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = colorResource(id = R.color.font_background_color1)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(modifier = Modifier.padding(start = 13.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = address, style = addressStyle)
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = colorResource(id = R.color.font_background_color2)
                        )
                        Text(text = "${trashCount} 개", style = trashCountStyle)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.DirectionsRun,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = colorResource(id = R.color.font_background_color2)
                        )
                        Text(text = distance, style = trashCountStyle)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.MoreTime,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = colorResource(id = R.color.font_background_color3)
                        )
                        Text(text = ploggingTime, style = timeCountStyle)
                    }
                }
            }
        }
    }
}