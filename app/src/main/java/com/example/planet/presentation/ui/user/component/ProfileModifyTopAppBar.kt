package com.example.planet.presentation.ui.user.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.util.noRippleClickable

@Composable
fun ProfileModifyTopAppBar(onBack: () -> Unit, updateUserInfo: () ->Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = colorResource(id = R.color.font_background_color1),
            modifier = Modifier
                .noRippleClickable {
                    onBack()
                }
        )
        Text(
            text = "프로필 수정",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = "완료",
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier
                .noRippleClickable { updateUserInfo() }
        )
    }
}