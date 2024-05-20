package com.example.planet.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
    verticalSpace: Dp = 0.dp,
) {
    val placeholderStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color1),
        fontWeight = FontWeight.SemiBold,
        fontSize = fontSize
    )

    Spacer(modifier = Modifier.height(verticalSpace))
    BasicTextField(modifier = modifier
        .fillMaxWidth()
        .background(
            color = colorResource(id = R.color.font_background_color4),
            shape = RoundedCornerShape(10.dp),
        ),
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        textStyle = placeholderStyle ,
        decorationBox = { innerTextField ->
            Row(
                modifier.padding(start = 16.dp, end = 9.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        text = placeholder,
                        style = placeholderStyle,
                        color = colorResource(id = R.color.font_background_color2)
                    )
                    innerTextField()
                }
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = colorResource(id = R.color.font_background_color1),
                    modifier = Modifier.size(23.dp)
                )
            }
        }
    )
    Spacer(modifier = Modifier.height(verticalSpace))

}