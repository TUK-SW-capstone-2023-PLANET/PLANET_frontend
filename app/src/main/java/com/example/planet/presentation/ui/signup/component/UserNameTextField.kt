package com.example.planet.presentation.ui.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun UserNameTextField(
    title: String,
    text: () -> String,
    onValueChange: (String) -> Unit,
    trailingText: () -> String,
    supportingText: String
) {
    val textColor = Color(0XFFC2C2C2)

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
            trailingIcon = {
                Text(
                    text = trailingText(),
                    modifier = Modifier,
                    style = LocalTextStyle.current.copy(
                        fontSize = 10.sp,
                        textAlign = TextAlign.End,
                        color = colorResource(id = R.color.font_background_color1),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            supportingText = {
                Text(text = supportingText, color = colorResource(id = R.color.red), fontSize = 10.sp)
            }
        )
    }
}