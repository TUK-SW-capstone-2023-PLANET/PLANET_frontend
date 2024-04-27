package com.example.planet.presentation.ui.user.component

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun GetPictureDialog(closeDialog: (Boolean) -> Unit, getImageUri: (Uri) -> Unit, getDefaultImage: () -> Unit) {
    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    uri -> getImageUri(uri)
                    closeDialog(false)
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

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.4f))
        .zIndex(1f)
        .noRippleClickable { closeDialog(false) }
    ) {
        Column(
            Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.BottomCenter)
                .noRippleClickable { /* Box의 Clickable을 방지하기 위함 */ }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(10.dp),
                color = colorResource(id = R.color.font_background_color4)
            ) {
                GetPictureDialogContent(
                    goOnGallery = { takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent) },
                    changeGeneralImage = { getDefaultImage() }
                )
            }
            TextButton(
                onClick = { closeDialog(false) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorResource(id = R.color.main_color1)
                )
            ) {
                Text(
                    text = "취소",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun GetPictureDialogContent(
    goOnGallery: () -> Unit = {},
    changeGeneralImage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = "프로필 사진 설정",
            fontSize = 12.sp,
            color = colorResource(id = R.color.font_background_color2)
        )

//        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = "앨범에서 사진 선택",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .noRippleClickable { goOnGallery() },
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.main_color1)
        )
        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Text(
            text = "기본 이미지로 변경",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .noRippleClickable { changeGeneralImage() },
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.main_color1)
        )
    }
}