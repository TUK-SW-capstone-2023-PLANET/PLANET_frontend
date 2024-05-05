package com.example.planet.presentation.ui.plogging.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R
import com.example.planet.data.remote.dto.response.plogging.PloggingTrashReceipt


@Composable
fun PloggingReceipt(receipt: List<Map<String, List<PloggingTrashReceipt>>>) {
    val timeTextStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color1),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(receipt.size) {
            val time = receipt[it].keys.toList()[0]
            val trashes = receipt[it].values.toList()[0]
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.font_background_color3)
                )
                Text(
                    text = time,
                    style = timeTextStyle,
                    modifier = Modifier.padding(start = 24.dp, bottom = 3.dp, top = 7.dp)
                )
                trashes.forEach { trashReceipt ->
                    TrashHistoryCard(trashReceipt)
                }
            }
        }
    }
}

@Composable
fun TrashHistoryCard(trashReceipt: PloggingTrashReceipt) {
    val mainTextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )
    val subTextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.font_background_color2)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0XFFDCDEF8))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = trashReceipt.imageUrl),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(7.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "${trashReceipt.name} ${trashReceipt.count}개",
                    style = mainTextStyle,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(text = trashReceipt.address, style = subTextStyle)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "+ ${trashReceipt.score}점", style = mainTextStyle)
            Text(text = "${trashReceipt.totalScore}점", style = subTextStyle)
        }
    }
}