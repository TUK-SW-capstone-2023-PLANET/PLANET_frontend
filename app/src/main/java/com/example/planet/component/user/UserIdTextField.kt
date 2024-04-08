package com.example.planet.component.user

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.component.common.RedTextButton

@Composable
fun UserIdTextField(
    headerText: String,
    textValue: String,
    enabled: Boolean = false,
    onValueChange: (String) -> Unit,
    placeholder: String,
    supportingText: @Composable () -> Unit,
) {


    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = headerText,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.2f),
            color = colorResource(id = R.color.font_background_color1),
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = {onValueChange(it)},
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = colorResource(id = R.color.main_color2),
                unfocusedContainerColor = colorResource(id = R.color.font_background_color3),
                focusedContainerColor = Color.White
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(0.6f)
                .height(45.dp),
            textStyle = TextStyle.Default.copy(fontSize = 10.sp),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 11.sp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            },
            trailingIcon = {
                TextButton(
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.main_color2),
                        contentColor = Color.White,
                        disabledContainerColor = colorResource(id = R.color.font_background_color4),
                        disabledContentColor = colorResource(id = R.color.font_background_color2),
                    ),
                    modifier = Modifier.padding(4.dp),
                    enabled = enabled,
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "중복검사", fontSize = 10.sp)
                }

            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        RedTextButton(text = "변경", modifier = Modifier.weight(0.2f))
    }
    supportingText()
}

@Composable
fun UserPwTextField(
    headerText: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    supportingText: @Composable () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = headerText,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.2f),
            color = colorResource(id = R.color.font_background_color1),
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = {onValueChange(it)},
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = colorResource(id = R.color.main_color2),
                unfocusedContainerColor = colorResource(id = R.color.font_background_color3),
                focusedContainerColor = Color.White
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(0.6f)
                .height(45.dp),
            textStyle = TextStyle.Default.copy(fontSize = 10.sp),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 11.sp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        RedTextButton(text = "변경", modifier = Modifier.weight(0.2f))
    }
    supportingText()
}

