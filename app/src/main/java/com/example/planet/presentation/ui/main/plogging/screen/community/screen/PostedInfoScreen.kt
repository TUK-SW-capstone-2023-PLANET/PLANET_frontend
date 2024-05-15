package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R
import com.example.planet.data.remote.dto.response.post.PostedInfo
import com.example.planet.presentation.ui.component.DialogComponent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedTopAppBar
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingCardIcons
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.viewmodel.CommunityViewModel

@Composable
fun PostedInfoScreen(
    viewModel: CommunityViewModel,
    navController: NavHostController,
    appBarTitle: String
) {
    val scrollState = rememberScrollState()

    if (viewModel.postedDialogState) {
        DialogComponent(
            title = "게시판 메뉴",
            text1 = "신고하기",
            text2 = "삭제하기",
            text2Color = colorResource(id = R.color.red),
            closeDialog = { viewModel.postedDialogState = false },
            onClick1 = { },
            onClick2 = { }
        )
    }

    BackHandler {
        if (viewModel.postedDialogState) viewModel.postedDialogState = false
        else navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostedTopAppBar(
            modifier = Modifier,
            title = appBarTitle,
            onBack = { navController.popBackStack() }) {
            viewModel.postedDialogState = true
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 18.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                    .fillMaxWidth()
            ) {
                PostedMyProfileCard(name = viewModel.postedInfo.nickName, date = viewModel.postedInfo.uploadTime)
                PostedContent(
                    title = viewModel.postedInfo.title,
                    image = viewModel.postedInfo.imageUrl,
                    content = viewModel.postedInfo.content,
                    heartCount = viewModel.postedInfo.heartCount,
                    commentCount = viewModel.postedInfo.commentCount,
                    personCount = viewModel.postedInfo.viewCount,
                    isFavorite = viewModel.postedInfo.heart
                ) {
                    viewModel.postedInfo = viewModel.postedInfo.copy(heart = it)
                }
            }

            Column {
                repeat(viewModel.postedInfo.commentCount) {
                    CommentCard(
                        image = painterResource(id = R.drawable.temporary_user_icon),
                        name = "행복한 티노",
                        content = "49 육회포자로 와라",
                        date = "35분 전",
                        heartCount = 21
                    )
                }
            }
        }

    }
}

@Composable
fun CommentCard(image: Painter, name: String, content: String, date: String, heartCount: Int) {
    val nameTextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
    val dateTextStyle = TextStyle(
        fontSize = 6.sp,
        color = colorResource(id = R.color.font_background_color2)
    )
    val contentTextStyle = TextStyle(
        fontSize = 10.sp,
    )
    val heartCountTextStyle = TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color3)
    )

    var isFavorite by remember {
        mutableStateOf(false)
    }


    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )

    val imageSize = 35.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(end = 16.dp)
                    .size(imageSize)
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Row(
                    modifier = Modifier.padding(bottom = 6.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = name,
                        style = nameTextStyle,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = date, style = dateTextStyle)
                }
                Text(text = content, style = contentTextStyle)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (!isFavorite) Icons.Outlined.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(20.dp)
                    .noRippleClickable { isFavorite = !isFavorite },
                tint = if (!isFavorite) colorResource(id = R.color.font_background_color3) else colorResource(
                    id = R.color.red
                )
            )
            Text(text = heartCount.toString(), style = heartCountTextStyle)
        }
    }

}


@Composable
fun PostedMyProfileCard(name: String, date: String) {
    val nameTextStyle =
        TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.font_background_color1))
    val dateTextStyle =
        TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.font_background_color2)
        )
    val imageSize = 50.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
            .height(imageSize)
    ) {
        Image(
            painter = painterResource(id = R.drawable.temporary_user_icon),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 17.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = name, style = nameTextStyle)
            Text(text = date, style = dateTextStyle)
        }
    }
}

@Composable
fun PostedContent(
    title: String,
    image: List<String>,
    content: String,
    heartCount: Int,
    commentCount: Int,
    personCount: Int,
    isFavorite: Boolean = false,
    favoriteOnClick: (Boolean) -> Unit
) {
    val titleTextStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val contentTextStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)

    Column {
        Text(text = title, style = titleTextStyle, modifier = Modifier.padding(bottom = 14.dp))

        if (image.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .padding(bottom = 14.dp)
                    .fillMaxWidth()
            ) {
                items(image.size) {
                    Image(
                        painter = rememberAsyncImagePainter(model = image[it]),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = if (it != 8) 6.dp else 0.dp)
                            .size(150.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                }
            }
        }


        Text(text = content, style = contentTextStyle, modifier = Modifier.padding(bottom = 6.dp))
        Row(modifier = Modifier.padding(bottom = 6.dp)) {
            PostingCardIcons(
                heartCount = heartCount,
                commentCount = commentCount,
                personCount = personCount
            )
        }
        PostedFavoriteButton(enabled = { isFavorite }) {
            favoriteOnClick(it)
        }

    }
}

@Composable
fun PostedFavoriteButton(enabled: () -> Boolean, onClick: (Boolean) -> Unit) {
    val iconSize = 11.dp
    val disableColor = colorResource(id = R.color.font_background_color2)
    val enableColor = colorResource(id = R.color.font_background_color2)
    val textStyle =
        TextStyle(fontSize = 11.sp, fontWeight = FontWeight.SemiBold)

    Card(
        modifier = Modifier.noRippleClickable { onClick(!enabled()) },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled()) colorResource(id = R.color.main_color2) else colorResource(
                id = R.color.font_background_color3
            ),
            contentColor = if (enabled()) Color.White else colorResource(id = R.color.font_background_color2)
        )
    ) {
        Row(
            modifier = Modifier.padding(top = 3.dp, bottom = 3.dp, start = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (enabled()) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(iconSize),
            )
            Text(text = "공감", style = textStyle)
        }
    }
}