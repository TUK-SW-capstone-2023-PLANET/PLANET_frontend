package com.example.planet.presentation.ui.signup.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.TitleText

@Composable
fun FinalScreen(goLoginActivity: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TitleText(
            accentText = "플래닛",
            generalText2 = "에 오신 것을\n환영합니다."
        )
        OutlinedButton(
            onClick = { goLoginActivity() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.main_color1),
            ),
            shape = RoundedCornerShape(9.dp),
            border =
                BorderStroke(width = 1.dp, color = colorResource(id = R.color.main_color1))
        ) {
            Text(text = "로그인 하러 가기 👋", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
}