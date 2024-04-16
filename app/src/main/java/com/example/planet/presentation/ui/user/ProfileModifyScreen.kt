package com.example.planet.presentation.ui.user

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.presentation.ui.user.component.MyProfileImage
import com.example.planet.presentation.ui.user.component.ProfileModifyTopAppBar
import com.example.planet.presentation.ui.user.component.UserIdTextField
import com.example.planet.presentation.ui.user.component.UserNicknameTextField
import com.example.planet.presentation.ui.user.component.UserPwTextField
import com.example.planet.util.noRippleClickable
import com.example.planet.presentation.viewmodel.UserViewModel
import com.example.planet.util.getImageUri
import com.example.planet.util.parseBitmap

@Composable
fun ProfileModifyScreen(userViewModel: UserViewModel, onClick: () -> Unit) {

    val scrollState = rememberScrollState()

    BackHandler {
        if (userViewModel.editNicknameState || userViewModel.editDescribeState) {
            if (userViewModel.editNicknameState) {
                userViewModel.changeEditNicknameScreen()
            } else {
                userViewModel.changeEditDescribeScreen()
            }
        } else {
            onClick()
        }
    }

    if (userViewModel.editNicknameState || userViewModel.editDescribeState) {
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
                                userViewModel.changeEditNicknameScreen()
                            } else {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileModifyTopAppBar {
            onClick()
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        MyProfileImage(setProfileImage = { userViewModel.myProfileImage = it }, profileImage = { userViewModel.myProfileImage })


        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Happy Been", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
            Image(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
                    .clickable {
                        userViewModel.changeEditNicknameScreen()
                    }
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 8.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "나랑 같이 플로깅 할 사람",
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
                    .noRippleClickable {
                        userViewModel.changeEditDescribeScreen()
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 8.dp),
            thickness = 2.dp,
            color = colorResource(id = R.color.font_background_color3)
        )

        Spacer(modifier = Modifier.height(9.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.exclamation_mark_logo),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "회원정보", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(9.dp))

        UserIdTextField(
            headerText = "아이디",
            textValue = { userViewModel.idTextValue },
            onValueChange = { userViewModel.idTextValue = it },
            placeholder = "TukoreaUniv0921"
        )

        UserPwTextField(
            headerText = "비밀번호",
            textValue = { userViewModel.pwTextValue },
            onValueChange = { userViewModel.pwTextValue = it },
            placeholder = "******"
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            thickness = 2.dp,
            color = colorResource(id = R.color.font_background_color3)
        )

        Text(
            text = "회원정보 삭제",
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            color = colorResource(id = R.color.red)
        )
    }
}