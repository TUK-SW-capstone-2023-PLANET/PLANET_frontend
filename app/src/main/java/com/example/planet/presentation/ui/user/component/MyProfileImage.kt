package com.example.planet.presentation.ui.user.component

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R
import com.example.planet.TAG

@Composable
fun MyProfileImage(setProfileImage: (Uri) -> Unit, profileImage: () -> Uri) {
    val context = LocalContext.current

    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    setProfileImage(uri)
                    Log.d(TAG, "serViewModel.myProfileImage: $profileImage")
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
                painter = if (profileImage() == Uri.EMPTY) {
                    painterResource(id = R.drawable.temporary_user_icon)
                } else {
                    rememberAsyncImagePainter(model = profileImage())
                },
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
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
}