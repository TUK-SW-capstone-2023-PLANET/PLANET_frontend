package com.example.planet.presentation.ui.user.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.planet.presentation.viewmodel.UserViewModel
import com.example.planet.util.noRippleClickable

@Composable
fun ShowEditTextField(userViewModel: UserViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "취소",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.noRippleClickable {
                    if (userViewModel.editNicknameState) {
                        userViewModel.changeEditNicknameScreen()
                    } else {
                        userViewModel.changeEditDescribeScreen()
                    }
                }
            )

            Text(
                text = "완료",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .noRippleClickable {
                        if (userViewModel.editNicknameState) {
                            userViewModel.userInfo = userViewModel.userInfo.copy(nickName = userViewModel.nicknameTextValue.text)
                            userViewModel.changeEditNicknameScreen()
                        } else {
                            userViewModel.userInfo = userViewModel.userInfo.copy(message = userViewModel.describeTextValue.text)
                            userViewModel.changeEditDescribeScreen()
                        }
                    })
            if (userViewModel.editNicknameState) {
                UserNicknameTextField(
                    text = userViewModel.nicknameTextValue,
                    onValueChange = {
                        if (it.text.length <= userViewModel.maxNicknameTextLength) {
                            userViewModel.nicknameTextValue = it
                        }
                    },
                    trailingText = userViewModel.nicknameTextLength,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ) {
                    if (userViewModel.editNicknameState) {
                        userViewModel.changeEditNicknameScreen()
                    } else {
                        userViewModel.changeEditDescribeScreen()
                    }
                }
            } else if (userViewModel.editDescribeState) {
                UserNicknameTextField(
                    text = userViewModel.describeTextValue,
                    onValueChange = {
                        if (it.text.length <= userViewModel.maxDescribeTextLength) {
                            userViewModel.describeTextValue = it
                        }
                    },
                    trailingText = userViewModel.describeTextLength,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ) {
                    if (userViewModel.editNicknameState) {
                        userViewModel.changeEditNicknameScreen()
                    } else {
                        userViewModel.changeEditDescribeScreen()
                    }
                }
            }

        }
    }
}