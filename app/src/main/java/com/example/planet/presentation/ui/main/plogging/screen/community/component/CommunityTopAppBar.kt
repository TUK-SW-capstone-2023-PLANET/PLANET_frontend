package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun BulletinBoardTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    goSearchScreen: () -> Unit
) {
    val iconColor = colorResource(id = R.color.font_background_color1)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp),
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
                        .noRippleClickable {
                            onBack()
                        }
                )

                Row {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = iconColor
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = iconColor
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

@Composable
fun PostedTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    onDialog: () -> Unit
) {
    val iconColor = colorResource(id = R.color.font_background_color1)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp),
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
                        .noRippleClickable {
                            onBack()
                        }
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.noRippleClickable { onDialog() }
                )
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