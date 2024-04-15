package com.example.planet.presentation.ui.signup.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.TitleText
import com.example.planet.presentation.ui.signup.component.UserNameTextField
import com.example.planet.presentation.viewmodel.SignUpViewModel


@Composable
fun PasswdScreen(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        TitleText(
            accentText = "비밀번호를 설정",
            generalText1 = "",
            generalText2 = "해주세요."
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            UserNameTextField(
                title = "8자리 이상 비밀번호를 입력해주세요.",
                text = { signUpViewModel.passwd },
                onValueChange = {
                    signUpViewModel.passwd = it
                },
                trailingText = { "" },
                supportingText = ""
            )
            UserNameTextField(
                title = "8자리 이상 비밀번호를 입력해주세요.",
                text = { signUpViewModel.realPasswd },
                onValueChange = {
                    signUpViewModel.realPasswd = it
                },
                trailingText = { "" },
                supportingText = ""
            )


        }
        OutlinedButton(
            onClick = { signUpViewModel.onNextPage(navController) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.main_color1),
                disabledContentColor = colorResource(id = R.color.font_background_color2),
                disabledContainerColor = Color.Transparent
            ),
            enabled = signUpViewModel.userNameIsNotEmpty,
            shape = RoundedCornerShape(9.dp),
            border = if (signUpViewModel.userNameIsNotEmpty) {
                BorderStroke(width = 1.dp, color = colorResource(id = R.color.main_color1))
            } else {
                BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            }
        ) {
            Text(text = "다음으로", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
}

@Composable
fun Test1(
    title: String,
    text: String,
    onValueChange: (String) -> Unit,
    supportingText: String
) {
    val textColor = Color(0XFFC2C2C2)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = textColor, fontSize = 12.sp)
        TextField(
            value = text,
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
            supportingText = {
                Text(
                    text = supportingText,
                    color = colorResource(id = R.color.red),
                    fontSize = 10.sp
                )
            }
        )
    }
}