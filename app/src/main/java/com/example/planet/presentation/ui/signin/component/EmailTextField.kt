package com.example.planet.presentation.ui.signin.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.planet.R

@Composable
fun EmailTextField(value: () -> String, onValueChange: (String) -> Unit, placeHolder: String) {
    TextField(
        value = value(),
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(id = R.color.main_color3),
            unfocusedIndicatorColor = colorResource(id = R.color.main_color3),
        ),
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(text = placeHolder, color = colorResource(id = R.color.font_background_color2))
        }
    )
}