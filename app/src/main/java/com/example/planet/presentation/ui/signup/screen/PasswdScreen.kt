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
import com.example.planet.presentation.ui.signup.component.NextButton
import com.example.planet.presentation.ui.signup.component.PasswdTextField
import com.example.planet.presentation.ui.signup.component.RealPasswdTextField
import com.example.planet.presentation.ui.signup.component.TitleText
import com.example.planet.presentation.ui.signup.component.UserNameTextField
import com.example.planet.presentation.viewmodel.SignUpViewModel


@Composable
fun PasswdScreen(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    val verticalScroll = rememberScrollState()

//    val buttonEnabled = (signUpViewModel.passwd.length >= 8) && (signUpViewModel.realPasswd == signUpViewModel.passwd)

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
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(verticalScroll),
            verticalArrangement = Arrangement.Center
        ) {
            PasswdTextField(
                title = "8자리 이상 비밀번호를 입력해주세요.",
                text = { signUpViewModel.passwd },
                onValueChange = {
                    signUpViewModel.passwd = it
                },
            )
            RealPasswdTextField(
                title = "한 번 더 입력해주세요.",
                passwd = { signUpViewModel.realPasswd },
                previousPw = { signUpViewModel.passwd },
                onValueChange = {
                    signUpViewModel.realPasswd = it
                },
            )
        }
        NextButton(enable = { (signUpViewModel.passwd.length >= 8) && (signUpViewModel.realPasswd == signUpViewModel.passwd) }) {
            signUpViewModel.onNextPage(navController)
        }
    }
}
