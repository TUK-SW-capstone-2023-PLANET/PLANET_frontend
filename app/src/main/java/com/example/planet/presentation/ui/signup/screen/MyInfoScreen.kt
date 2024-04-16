package com.example.planet.presentation.ui.signup.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.GenderSwitch
import com.example.planet.presentation.ui.signup.component.MyInfoTextField
import com.example.planet.presentation.ui.signup.component.TitleText
import com.example.planet.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun MyInfoScreen(navController: NavHostController, signUpViewModel: SignUpViewModel) {
    val verticalScroll = rememberScrollState()
    val textColor = Color(0XFFC2C2C2)

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        TitleText(
            accentText = "당신의 정보",
            generalText1 = "거의 다 끝났어요!\n",
            generalText2 = "를 입력해주세요!"
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(verticalScroll)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "성별", fontSize = 12.sp, color = textColor)
            Spacer(modifier = Modifier.height(6.dp))

            GenderSwitch(
                isMale = { signUpViewModel.gender },
                genderChange = { signUpViewModel.gender = it })
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                MyInfoTextField(
                    modifier = Modifier.weight(1f),
                    title = "키",
                    text = { signUpViewModel.height },
                    onValueChange = { signUpViewModel.height = it }
                )
                Spacer(modifier = Modifier.width(24.dp))
                MyInfoTextField(
                    modifier = Modifier.weight(1f),
                    title = "몸무게",
                    text = { signUpViewModel.weight },
                    onValueChange = { signUpViewModel.weight = it }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedButton(
                onClick = { signUpViewModel.standardHeightAndWeight() },
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.main_color1)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(id = R.color.main_color1),
                    containerColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "표준으로 설정")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "정확한 키, 몸무게는 정확한 칼로리를 알려줘요!", fontSize = 12.sp, color = textColor)
        }
        OutlinedButton(
            onClick = {
                scope.launch {
                    signUpViewModel.postCreateUser()
                    if (signUpViewModel.userId.isNotEmpty()) {
                        signUpViewModel.onNextPage(navController)
                    }
                } },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorResource(id = R.color.main_color1),
                disabledContentColor = colorResource(id = R.color.font_background_color2),
                disabledContainerColor = Color.Transparent
            ),
            enabled = signUpViewModel.authCodeIsNotEmpty,
            shape = RoundedCornerShape(9.dp),
            border = if (signUpViewModel.authCodeIsNotEmpty) {
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
