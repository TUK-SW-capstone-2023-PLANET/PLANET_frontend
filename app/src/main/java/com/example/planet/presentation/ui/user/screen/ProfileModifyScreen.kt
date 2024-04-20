package com.example.planet.presentation.ui.user.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.user.component.GetPictureDialog
import com.example.planet.presentation.ui.user.component.HeightWeightRow
import com.example.planet.presentation.ui.user.component.MyProfileImage
import com.example.planet.presentation.ui.user.component.ProfileModifyTopAppBar
import com.example.planet.presentation.ui.user.component.ShowEditTextField
import com.example.planet.presentation.ui.user.component.UserIdTextField
import com.example.planet.presentation.ui.user.component.UserPwTextField
import com.example.planet.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileModifyScreen(userViewModel: UserViewModel, onClick: () -> Unit) {

    LaunchedEffect(Unit) {
        userViewModel.getUserInfo()
    }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (userViewModel.dialogState) {
        GetPictureDialog(
            closeDialog = { userViewModel.dialogState = false },
            getImageUri = {
                scope.launch { userViewModel.getImageUrl(uri = it, context = context) }
            },
            getDefaultImage = { userViewModel.changeDefaultImage() }
            )
    }

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
        ShowEditTextField(userViewModel = userViewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileModifyTopAppBar(onBack = { onClick() }) {
            scope.launch {
                userViewModel.putUserInfo(context)
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        MyProfileImage(
            isShowDialog = { userViewModel.dialogState = true },
            profileImage = { userViewModel.userInfo.imageUrl }
        )


        Spacer(modifier = Modifier.height(16.dp))


        UserNameRow(name = { userViewModel.userInfo.nickName }, fontSize = 16.sp) {
            userViewModel.changeEditNicknameScreen()

        }


        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 8.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )

        UserStateMessageRow(name = { userViewModel.userInfo.message }, fontSize = 11.sp) {
            userViewModel.changeEditDescribeScreen()

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
            placeholder = userViewModel.userInfo.email
        )

        UserPwTextField(
            headerText = "비밀번호",
            textValue = { userViewModel.pwTextValue },
            onValueChange = { userViewModel.pwTextValue = it },
            placeholder = userViewModel.userInfo.passwd
        )

        HeightWeightRow(
            heightText = { userViewModel.heightTextValue },
            heightTextValue = { userViewModel.heightTextValue = it },
            heightPlaceholder = userViewModel.userInfo.height.toString(),
            weightText = { userViewModel.weightTextValue },
            weightTextValue = { userViewModel.weightTextValue = it },
            weightPlaceholder = userViewModel.userInfo.weight.toString()
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

@Composable
fun UserNameRow(name: () -> String, fontSize: TextUnit, onEditText: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = name(), fontSize = fontSize, modifier = Modifier.align(Alignment.Center))
        Image(
            painter = painterResource(id = R.drawable.edit_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(20.dp)
                .clickable {
                    onEditText()
                }
        )
    }
}

@Composable
fun UserStateMessageRow(name: () -> String, fontSize: TextUnit, onEditText: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = name(), fontSize = fontSize, modifier = Modifier.align(Alignment.Center))
        Image(
            painter = painterResource(id = R.drawable.edit_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(20.dp)
                .clickable {
                    onEditText()
                }
        )
    }
}