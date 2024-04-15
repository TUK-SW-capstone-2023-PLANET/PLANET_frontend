package com.example.planet.presentation.ui.signup.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.util.noRippleClickable

@Composable
fun RealPasswdTextField(
    title: String,
    passwd: () -> String,
    previousPw: () -> String,
    onValueChange: (String) -> Unit,
) {
    val textColor = Color(0XFFC2C2C2)

    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Text(text = title, color = textColor, fontSize = 12.sp)
        TextField(
            value = passwd(),
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            trailingIcon = {
                val icon =
                    Icon(
                        imageVector = if (showPassword) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Visibility",
                        modifier = Modifier.noRippleClickable { showPassword = !showPassword }
                    )
            },
            supportingText = {
                val (supportingText, supportingTextColor) =
                    if (passwd().isEmpty()) {
                        Pair("", Color.Transparent)
                    } else if (passwd() != previousPw()) {
                        Pair("비밀번호가 일치하지 않아요!", colorResource(id = R.color.red))
                    } else {
                        Pair("비밀번호가 일치해요!", colorResource(id = R.color.main_color1))
                    }
                Log.d(TAG, "supportingText: $supportingText, $supportingTextColor")

                Text(
                    text = supportingText,
                    color = supportingTextColor,
                    fontSize = 10.sp
                )
            }
        )
    }
}