package com.example.planet.presentation.ui.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.planet.util.noRippleClickable

@Composable
fun PasswdTextField(
    title: String = "",
    text: () -> String,
    onValueChange: (String) -> Unit,
    placeholder: String = ""
) {
    val textColor = Color(0XFFC2C2C2)

    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (title.isNotEmpty()) Text(text = title, color = textColor, fontSize = 12.sp)
        TextField(
            value = text(),
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
            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(text = placeholder, color = colorResource(id = R.color.font_background_color2))
                }
            },
            supportingText = {
//                Text(text = "8자리 이상 입력하세요", color = colorResource(id = R.color.red), fontSize = 10.sp)
                Text(
                    text = if (text().length in 1..7) "8자리 이상 입력하세요" else "",
                    color = colorResource(id = R.color.red),
                    fontSize = 10.sp
                )
            }
        )
    }
}