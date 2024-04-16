package com.example.planet.presentation.ui.signup.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.data.remote.dto.response.signup.LoginAuthState
import com.example.planet.util.noRippleClickable

@Composable
fun AuthCodeTextField(
    title: String,
    text: () -> String,
    onValueChange: (String) -> Unit,
    suffix: () -> String,
    isAuthCode: () -> LoginAuthState
) {
    val textColor = Color(0XFFC2C2C2)

    val (supportingText, supportingTextColor) = if (isAuthCode() == LoginAuthState.Fail) {
        Pair("올바른 인증코드가 아닙니다.", colorResource(id = R.color.red))
    } else {
        Pair("", Color.Transparent)
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = textColor, fontSize = 12.sp)
        TextField(
            value = text(),
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            singleLine = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            suffix = {
                Text(text = suffix())
            },
            supportingText = {
                Text(text = supportingText, color = supportingTextColor, fontSize = 10.sp)
            }
        )
    }
}