package com.example.planet.presentation.ui.main.plogging.screen.message.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.planet.R

@Composable
fun MessageDialog(
    title: String,
    content1: String,
    content2: String,
    icon1: ImageVector,
    icon2: ImageVector,
    onClick: () -> Unit,
    onClose: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    Dialog(onDismissRequest = { onClose(false) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            MessageDialogContent(
                title = title,
                content1 = content1,
                content2 = content2,
                icon1 = icon1,
                icon2 = icon2,
                onClick = { onClick() },) {
                onClose(false)
            }

        }
    }
}

@Composable
fun MessageDialogContent(
    title: String,
    content1: String,
    content2: String,
    icon1: ImageVector,
    icon2: ImageVector,
    onClick: () -> Unit,
    closeOnDialog: () -> Unit
) {
    Column(
        modifier = Modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = title, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.font_background_color3))
                        .clickable { closeOnDialog() }
                ) {
                    Icon(
                        imageVector = icon1,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Text(text = content1, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            }
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.red))
                        .clickable {
                            onClick()
                        }
                ) {
                    Icon(
                        imageVector = icon2,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
                Text(text = content2, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
