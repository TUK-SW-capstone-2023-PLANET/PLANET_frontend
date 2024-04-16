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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.data.remote.dto.response.signup.LoginAuthState
import com.example.planet.presentation.ui.signup.component.TitleText
import com.example.planet.presentation.ui.signup.component.UserNameTextField
import com.example.planet.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun NameScreen(navController: NavHostController, signUpViewModel: SignUpViewModel) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        TitleText(
            accentText = "이름",
            generalText1 = "당신의 ",
            generalText2 = "은\n무엇인가요?"
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            UserNameTextField(
                title = "원하는 이름을 정해주세요!",
                text = { signUpViewModel.userName },
                onValueChange = {
                    if (it.length <= signUpViewModel.maxUsernameTextLength)
                        signUpViewModel.userName = it
                },
                trailingText = { signUpViewModel.usernameTextLength },
                isNameDuplicated = { signUpViewModel.isUserNameCheck }
            )
            OutlinedButton(
                onClick = {
                    scope.launch {
                        signUpViewModel.getDuplicatedNameCheck()
                    }
                },
                modifier = Modifier.align(alignment = Alignment.End),
                border = if (signUpViewModel.userNameIsNotEmpty) BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.main_color1)
                ) else null,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.main_color1),
                    containerColor = Color.Transparent,
                    disabledContainerColor = colorResource(id = R.color.font_background_color2),
                    disabledContentColor = Color.White,
                ),
                enabled = signUpViewModel.userNameIsNotEmpty,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "중복체크")
            }
        }
        OutlinedButton(
            onClick = {
                if (signUpViewModel.isUserNameCheck == LoginAuthState.Success) {
                    signUpViewModel.onNextPage(navController)
                }
            },
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

