package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingTopAppBar
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.viewmodel.CommunityViewModel
import kotlinx.coroutines.launch

@Composable
fun PostingScreen(
    viewModel: CommunityViewModel,
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val count = result.data?.clipData?.itemCount!!
                for (i in 0 until count) {
                    val uri = result.data?.clipData?.getItemAt(i)?.uri!!
                    viewModel.postingImageList += uri
                }
            } else if (result.resultCode != Activity.RESULT_CANCELED) {
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
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostingTopAppBar(
            modifier = Modifier,
            title = "글쓰기",
            onBack = { navController.popBackStack() }
        ) {
            scope.launch {
                viewModel.savePosting{ navController.popBackStack() }
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 18.dp, start = 20.dp, end = 20.dp)
//                .fillMaxSize()
                .weight(1f)
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Vertical)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.postingImageList.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.empty_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .noRippleClickable {
                            takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                        }
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .padding(bottom = 14.dp)
                        .fillMaxWidth()
                ) {
                    items(viewModel.postingImageList.size) {
                        Box(modifier = Modifier.size(150.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(model = viewModel.postingImageList[it]),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .fillMaxSize()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Card(
                                modifier = Modifier
                                    .padding(end = 2.dp)
                                    .size(20.dp)
                                    .align(Alignment.TopEnd)
                                    .noRippleClickable {
                                        val test = viewModel.postingImageList.toMutableList()
                                        test.removeAt(it)
                                        viewModel.postingImageList = test
                                    },
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                ),
                                border = BorderStroke(width = 1.dp, color = Color.Black)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .size(150.dp)
                                .padding(end = 2.dp)
                                .noRippleClickable {
                                    takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                                },
                            shape = RoundedCornerShape(6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.font_background_color3),
                                contentColor = Color.White
                            ),
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
            PostingTitleTextFiled(
                value = viewModel.postingTitleInput,
                onValueChange = { viewModel.postingTitleInput = it })
            PostingContentTextFiled(
//                modifier = Modifier.weight(1f),
                value = viewModel.postingContentInput,
                onValueChange = { viewModel.postingContentInput = it })
        }
    }
}

@Composable
fun PostingTitleTextFiled(value: String, onValueChange: (String) -> Unit) {

    val placeholderTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = colorResource(id = R.color.font_background_color3),
            focusedIndicatorColor = colorResource(id = R.color.font_background_color3),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        placeholder = { Text(text = "제목", style = placeholderTextStyle) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    )
}

@Composable
fun PostingContentTextFiled(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {

    val placeholderTextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        placeholder = { Text(text = "내용을 입력하세요.", style = placeholderTextStyle) },
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
    )
}