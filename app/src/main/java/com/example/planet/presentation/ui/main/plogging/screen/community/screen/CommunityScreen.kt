package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun CommunityScreen(
    startCommunityActivity: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 21.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .weight(0.45f)
                .background(color = colorResource(id = R.color.font_background_color4))
                .padding(start = 30.dp, end = 30.dp, top = 24.dp, bottom = 30.dp)
        ) {
            BulletinBoardChoiceTitle(text = "원하는 게시판을 선택해주세요")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                BulletinBoardCard(
                    title = "자유 게시판",
                    description = "플로깅 회원들 모두와\n" + "소통이 가능한 게시판",
                    image = painterResource(id = R.drawable.university2),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(20.dp))
                BulletinBoardCard(
                    title = "대학교 게시판",
                    description = "자대 학생들끼리\n" + "소통이 가능한 게시판",
                    image = painterResource(id = R.drawable.university2),
                    modifier = Modifier.weight(1f)
                )
            }

//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.8f)
//            ) {
//                PopularPosting(
//                    text = "플로깅 10년차의 플로깅 꿀팁!",
//                    icon = Icons.Outlined.Person,
//                    count = 1000,
//                    modifier = Modifier
//                )
//                Spacer(modifier = Modifier.height(9.dp))
//                PopularPosting(
//                    text = "플로깅 10년차의 플로깅 꿀팁!",
//                    icon = Icons.Outlined.Person,
//                    count = 1000,
//                    modifier = Modifier
//                )
//            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 30.dp, end = 30.dp, top = 19.dp)
        ) {
            val titleStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 23.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fire_icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Text(text = "핫 게시글", style = titleStyle)
            }
            val dummyDate = listOf(
                PostingInfo(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                ),
                PostingInfo(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                ),
                PostingInfo(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                )
            )
            dummyDate.forEach {
                HotPostingCard(
                    image = it.image,
                    name = it.name,
                    date = it.date,
                    title = it.title,
                    content = it.content
                )
            }

        }
    }
}

data class PostingInfo(
    val image: Painter,
    val name: String,
    val date: String,
    val title: String,
    val content: String
)

@Composable
fun HotPostingCard(
    modifier: Modifier = Modifier,
    image: Painter,
    name: String,
    date: String,
    title: String,
    content: String
) {
    val nameTextStyle = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)
    val dateTextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color1)
    )
    val titleTextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    val contentTextStyle = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)

    Column(modifier = modifier.wrapContentSize()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = name, style = nameTextStyle)
                Text(text = date, style = dateTextStyle)
            }
        }
        Column(
            modifier
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Text(text = title, style = titleTextStyle)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = content, style = contentTextStyle)
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3),
            modifier = Modifier.padding(bottom = 14.dp)
        )
    }
}

@Composable
fun BulletinBoardChoiceTitle(text: String) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )

    Text(text = text, style = textStyle, modifier = Modifier.padding(bottom = 14.dp))
}

@Composable
fun BulletinBoardCard(title: String, description: String, image: Painter, modifier: Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(0.95f), shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 19.dp, start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.font_background_color1)
                )
            )

        }
    }
}

@Composable
fun PopularPosting(text: String, icon: ImageVector, count: Int, modifier: Modifier) {
    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold
    )

    val countStyle = TextStyle(
        fontSize = 10.sp,
        color = Color(0xFFFF0000),
        fontWeight = FontWeight.Normal
    )

    val iconColor = Color(0xffffaca3)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fire_icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Yellow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = text, style = textStyle, modifier = Modifier.padding(end = 6.dp))
                Icon(imageVector = icon, contentDescription = null, tint = iconColor)
                Text(text = "$count +", style = countStyle)
            }
        }

    }
}
