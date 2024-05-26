package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.viewmodel.CommunityViewModel
import kotlinx.coroutines.launch

@Composable
fun CommentCard(
    viewModel: CommunityViewModel,
    commentId: Long,
    userId: Long,
    myUserId: Long,
    image: String,
    name: String,
    content: String,
    date: String,
    isHeart: Boolean,
    heartCount: Int
) {
    var scope = rememberCoroutineScope()

    var dropDownMenuState by remember {
        mutableStateOf(false)
    }

    val nameTextStyle = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
    )
    val dateTextStyle = TextStyle(
        fontSize = 8.sp,
        color = colorResource(id = R.color.font_background_color2)
    )
    val contentTextStyle = TextStyle(
        fontSize = 11.sp,
    )
    val heartCountTextStyle = TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.red)
    )

    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )

    val imageSize = 38.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(imageSize)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

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
                Text(
                    text = content,
                    style = contentTextStyle,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                if (heartCount >= 1) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .size(10.dp),
                            tint = colorResource(id = R.color.red)
                        )
                        Text(text = heartCount.toString(), style = heartCountTextStyle)
                    }
                }
            }
        }
        Box(modifier = Modifier) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .noRippleClickable { dropDownMenuState = true },
                tint = colorResource(id = R.color.font_background_color1)
            )
            CommentDropDownMenu(
                expanded = dropDownMenuState,
                onDismissRequest = { dropDownMenuState = false },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                isFavorite = { isHeart },
                isMyComment = { userId == myUserId },
                onSend = {},
                onFavorite = {
                    scope.launch {
                        viewModel.saveCommentHeart(commentId)
                    }
                },
                onDeleteFavorite = {
                    scope.launch {
                        viewModel.deleteCommentHeart(commentId)
                    }
                },
                onDeleteComment = {
                    scope.launch {
                        viewModel.deleteComment(commentId) { dropDownMenuState = false }
                    }
                },
                onReport = {}
            )
        }
    }
}


@Composable
fun PostedMyProfileCard(image: String, name: String, date: String) {
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
        Card(
            shape = CircleShape,
            modifier = Modifier.size(imageSize)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

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
                    Card(
                        modifier = Modifier
                            .padding(end = if (it != 8) 6.dp else 0.dp)
                            .size(150.dp)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = image[it]),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }


        Text(text = content, style = contentTextStyle, modifier = Modifier.padding(bottom = 6.dp))
        Row(modifier = Modifier.padding(bottom = 6.dp)) {
            PostingCardIcons(
                heartCount = heartCount,
                commentCount = commentCount,
                viewCount = personCount
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