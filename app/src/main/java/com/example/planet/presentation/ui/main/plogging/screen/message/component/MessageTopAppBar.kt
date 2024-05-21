package com.example.planet.presentation.ui.main.plogging.screen.message.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable


@Composable
fun MessageTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    goSendScreen: () -> Unit,
    onQuit: () -> Unit
) {
    val iconColor = colorResource(id = R.color.font_background_color1)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .size(30.dp)
                        .noRippleClickable {
                            onBack()
                        }
                )

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.send_icon2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .noRippleClickable {
                                goSendScreen()
                            }
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier
                            .size(30.dp)
                            .noRippleClickable {
                                onQuit()
                            }
                    )
                }
            }
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}