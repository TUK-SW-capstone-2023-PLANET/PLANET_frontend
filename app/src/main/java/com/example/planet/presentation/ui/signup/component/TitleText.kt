package com.example.planet.presentation.ui.signup.component

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun TitleText(accentText: String, generalText1: String = "", generalText2: String = "") {
    Text(text = buildAnnotatedString {
        append(generalText1)
        withStyle(SpanStyle(color = colorResource(id = R.color.main_color1))) {
            append(accentText)
        }
        append(generalText2)
    }, fontSize = 24.sp, fontWeight = FontWeight.Bold)
}