package com.example.planet.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun DialogComponent(
    title: String,
    text1: String,
    text2: String,
    text2Color: Color = colorResource(id = R.color.main_color1),
    closeDialog: (Boolean) -> Unit,
    onClick1: () -> Unit,
    onClick2: () -> Unit
) {


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.4f))
        .zIndex(1f)
        .noRippleClickable { closeDialog(false) }
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter)
                .noRippleClickable { /* Box의 Clickable을 방지하기 위함 */ }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(10.dp),
                color = colorResource(id = R.color.font_background_color4)
            ) {
                GetPictureDialogContent(
                    title = title,
                    text1 = text1,
                    text2 = text2,
                    text2Color = text2Color,
                    goOnGallery = {
                        onClick1()
                        closeDialog(false)
                    },
                    changeGeneralImage = { onClick2() }
                )
            }
            TextButton(
                onClick = { closeDialog(false) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.main_color1)
                )
            ) {
                Text(
                    text = "취소",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.main_color1)
                )
            }
        }
    }
}

@Composable
fun GetPictureDialogContent(
    title: String,
    text1: String,
    text2: String,
    text2Color: Color = colorResource(id = R.color.main_color1),
    goOnGallery: () -> Unit = {},
    changeGeneralImage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = title,
            fontSize = 12.sp,
            color = colorResource(id = R.color.font_background_color2)
        )

//        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = text1,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .noRippleClickable { goOnGallery() },
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.main_color1)
        )
        if (text2.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Text(
                text = text2,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .noRippleClickable { changeGeneralImage() },
                textAlign = TextAlign.Center,
                color = text2Color
            )
        }
    }
}