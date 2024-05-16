package com.example.planet.presentation.ui.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun MyInfoTextField(
    modifier: Modifier,
    title: String,
    text: () -> String,
    onValueChange: (String) -> Unit,
) {
    val textColor = Color(0XFFC2C2C2)

    Column(modifier = modifier) {
        Text(text = title, fontSize = 12.sp, color = textColor)
        TextField(
            value = text(),
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            suffix = {
                Text(
                    text = "KG",
                    modifier = Modifier,
                    style = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        color = colorResource(id = R.color.font_background_color1),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
        )
    }
}