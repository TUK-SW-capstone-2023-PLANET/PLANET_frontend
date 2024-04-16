package com.example.planet.presentation.ui.signup.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.EmailTextField
import com.example.planet.presentation.ui.signup.component.SignUpTextField
import com.example.planet.presentation.ui.signup.component.TitleText
import com.example.planet.presentation.ui.signup.component.UniversityTextField
import com.example.planet.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    val textColor = Color(0XFFC2C2C2)

    val timerValue by signUpViewModel.authTime.collectAsState()
    val verticalScroll = rememberScrollState()
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        TitleText(
            accentText = "대학",
            generalText1 = "소중한 마음을 가진\n당신의 ",
            generalText2 = "은 어디인가요? "
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = buildAnnotatedString {
            withStyle(SpanStyle(color = colorResource(id = R.color.main_color1))) {
                append("플래닛")
            }
            append("은 서로 즐거운 플로깅을 위해 사용자의 대학교를 인증받아요.")
        }, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = textColor)

        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(verticalScroll), verticalArrangement = Arrangement.Center) {

            UniversityTextField(
                title = "대학교명을 입력해주세요.",
                text = { signUpViewModel.university },
                onValueChange = { signUpViewModel.university = it },
                enable = { signUpViewModel.universityIsNotEmpty },
                isUniversity = { signUpViewModel.isUniversityCheck},
            )
            OutlinedButton(
                onClick = { scope.launch{ signUpViewModel.universityCheck() } },
                modifier = Modifier.align(alignment = Alignment.End),
                border = if (signUpViewModel.universityIsNotEmpty) BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.main_color1)
                ) else null,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.main_color1),
                    containerColor = Color.Transparent,
                    disabledContainerColor = colorResource(id = R.color.font_background_color2),
                    disabledContentColor = Color.White,
                ),
                enabled = signUpViewModel.universityIsNotEmpty,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "인증하기")
            }


            EmailTextField(
                title = "대학교 이메일을 입력해주세요.",
                text = { signUpViewModel.email },
                onValueChange = { signUpViewModel.email = it },
                enable = { signUpViewModel.emailIsNotEmpty },
                isUniversity = { signUpViewModel.isEmailCheck }
            )

            OutlinedButton(
                onClick = { scope.launch { signUpViewModel.getAuthCode() } },
                modifier = Modifier.align(alignment = Alignment.End),
                border = if (signUpViewModel.emailIsNotEmpty) BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.main_color1)
                ) else null,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.main_color1),
                    containerColor = Color.Transparent,
                    disabledContainerColor = colorResource(id = R.color.font_background_color2),
                    disabledContentColor = Color.White,
                ),
                enabled = signUpViewModel.emailIsNotEmpty,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "인증하기")
            }

            SignUpTextField(
                title = "메일로 받은 인증번호를 입력해주세요.",
                text = { signUpViewModel.authNumber },
                onValueChange = { signUpViewModel.authNumber = it },
                enable = { signUpViewModel.authNumberIsNotEmpty },
                supportingText = { "" },
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
            enabled = signUpViewModel.authNumberIsNotEmpty,
            shape = RoundedCornerShape(9.dp),
            border = if (signUpViewModel.authNumberIsNotEmpty) {
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