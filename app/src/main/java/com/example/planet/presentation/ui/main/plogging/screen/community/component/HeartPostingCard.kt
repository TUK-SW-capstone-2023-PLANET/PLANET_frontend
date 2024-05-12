package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun HeartPostingCard(
    text: String,
    count: Int,
    modifier: Modifier
) {
    val containerColor = Color(0xFFFFD3D3)
    val iconColor = Color(0xFFFF5D5D)

    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold
    )

    val countStyle = TextStyle(
        fontSize = 10.sp,
        color = Color(0xFFFF0000),
        fontWeight = FontWeight.Normal
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fire_icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = text, style = textStyle, modifier = Modifier.padding(end = 6.dp))
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "$count +", style = countStyle)
            }
        }

    }
}
@Composable
fun VisitPostingCard(
    text: String,
    count: Int,
    modifier: Modifier
) {
    val containerColor = Color(0xFFFFD3D3)
    val iconColor = Color(0xFFFF5D5D)

    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold
    )

    val countStyle = TextStyle(
        fontSize = 10.sp,
        color = Color(0xFFFF0000),
        fontWeight = FontWeight.Normal
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart_icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = text, style = textStyle, modifier = Modifier.padding(end = 6.dp))
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "$count +", style = countStyle)
            }
        }

    }
}