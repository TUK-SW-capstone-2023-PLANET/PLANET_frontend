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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import com.example.planet.presentation.ui.user.component.UserIdTextField
import com.example.planet.presentation.ui.user.component.UserNicknameTextField
import com.example.planet.presentation.ui.user.component.UserPwTextField
import com.example.planet.util.noRippleClickable
import com.example.planet.presentation.viewmodel.UserViewModel

@Composable
fun ProfileModifyScreen(userViewModel: UserViewModel, onClick: () -> Unit) {

    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    Log.d(TAG, "uri: $uri")
                }
            } else if (result.resultCode != Activity.RESULT_CANCELED) {
//                toast(context, StringAsset.Toast.ErrorTakenPhoto)
            }
        }
    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
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
                    })

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = colorResource(id = R.color.font_background_color1),
            modifier = Modifier
                .align(Alignment.TopStart)
                .noRippleClickable {
                    onClick()
                }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "프로필 수정",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        Box(modifier = Modifier.wrapContentSize()) {
            Card(
                modifier = Modifier
                    .size(115.dp)
                    .aspectRatio(1f), shape = CircleShape
            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(userIconUrl)
//                        .crossfade(true).build(),
//                    contentDescription = null,
//                    modifier = Modifier
//                )
                Image(
                    painter = painterResource(id = R.drawable.temporary_user_icon),
                    contentDescription = null,
                )
            }
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.BottomEnd),
                elevation = CardDefaults.elevatedCardElevation(2.dp)
            ) {
                IconButton(
                    onClick = { takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent) },
                    modifier = Modifier.fillMaxSize(),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }


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
            textValue = userViewModel.idTextValue.value,
            onValueChange = { userViewModel.idTextValue.value = it },
            enabled = userViewModel.duplicateCheck,
            placeholder = "TukoreaUniv0921"
        ) {
            Text(
                text = "*아이디는 특수문자 사용이 불가능합니다.",
                fontSize = 8.sp,
                color = colorResource(id = R.color.font_background_color2)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        UserPwTextField(
            headerText = "비밀번호",
            textValue = userViewModel.pwTextValue.value,
            onValueChange = { userViewModel.pwTextValue.value = it },
            placeholder = "******"
        ) {
            Text(
                text = "* 특수문자, 숫자, 대문자를 포함하여 8자 이상",
                fontSize = 8.sp,
                color = colorResource(id = R.color.font_background_color2)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

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