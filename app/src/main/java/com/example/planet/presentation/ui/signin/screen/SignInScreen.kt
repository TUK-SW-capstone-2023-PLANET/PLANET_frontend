package com.example.planet.presentation.ui.signin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.signin.component.EmailTextField
import com.example.planet.presentation.ui.signup.component.PasswdTextField
import com.example.planet.presentation.viewmodel.SignInViewModel
import com.example.planet.util.noRippleClickable
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel,
    startSignUpActivity: () -> Unit,
    startMainActivity: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 72.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.planet_logo), contentDescription = null)
        EmailTextField(
            value = { signInViewModel.email },
            onValueChange = { signInViewModel.email = it },
            placeholder = "email"
        )

        PasswdTextField(
            text = { signInViewModel.password },
            onValueChange = { signInViewModel.password = it },
            placeholder = "password"
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (signInViewModel.autoLoginState.value) {
                    Icons.Default.CheckCircle
                } else {
                    Icons.Outlined.CheckCircle
                },
                contentDescription = null,
                tint = colorResource(id = R.color.main_color2),
                modifier = Modifier.noRippleClickable {
                    signInViewModel.changeAutoLoginState()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "로그인 상태 유지", color = colorResource(id = R.color.font_background_color2))
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    signInViewModel.login { startMainActivity() }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.main_color1)
            ),
            shape = RoundedCornerShape(2.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
        ) {
            Text(text = "로그인", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.wrapContentSize()) {
            Text(
                text = "플래닛이 처음인가요? ",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp
            )
            Text(
                text = "회원가입",
                fontSize = 12.sp,
                color = colorResource(id = R.color.main_color1),
                modifier = Modifier.noRippleClickable { startSignUpActivity() })
        }
    }
}