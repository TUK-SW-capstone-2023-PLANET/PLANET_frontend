package com.example.planet.presentation.ui.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MyProfileImage(profileImage: () -> String, isShowDialog: (Boolean) -> Unit) {

    Box(modifier = Modifier.wrapContentSize()) {
        Card(
            modifier = Modifier
                .size(115.dp)
                .aspectRatio(1f), shape = CircleShape
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = profileImage()),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd),
            elevation = CardDefaults.elevatedCardElevation(2.dp)
        ) {
            IconButton(
                onClick = { isShowDialog(false) },
                modifier = Modifier.fillMaxSize(),
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}