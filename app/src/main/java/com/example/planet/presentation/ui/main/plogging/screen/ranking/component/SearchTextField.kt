package com.example.planet.presentation.ui.main.plogging.screen.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.planet.R

@Composable
fun SearchTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    placeholder: String = "",
    trailingIcon: (@Composable () -> Unit)? = null
) {
    BasicTextField(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .background(
            color = colorResource(id = R.color.font_background_color4),
            shape = CircleShape,
        ),
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
//        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        text = placeholder,
                        style = LocalTextStyle.current.copy(fontSize = fontSize),
                        color = colorResource(id = R.color.font_background_color2)
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}