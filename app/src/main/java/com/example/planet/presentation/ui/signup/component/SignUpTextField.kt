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
import com.example.planet.util.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpTextField(
    title: String,
    text: () -> String,
    onValueChange: (String) -> Unit,
    enable: () -> Boolean,
    supportingText: String
) {
    val textColor = Color(0XFFC2C2C2)

//    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = textColor, fontSize = 12.sp)
        TextField(
            value = text(),
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
//                .bringIntoViewRequester(bringIntoViewRequester)
//                .onFocusEvent { focusState ->
//                    if (focusState.isFocused) {
//                        coroutineScope.launch {
//                            bringIntoViewRequester.bringIntoView()
//                        }
//                    }
//                },
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
                if (enable()) {
                    Log.d(TAG, "signUpViewModel.email: $enable")
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable { onValueChange("") })
                }
            },
            supportingText = {
                Text(text = supportingText, color = colorResource(id = R.color.red), fontSize = 10.sp)
            }
        )
    }
}