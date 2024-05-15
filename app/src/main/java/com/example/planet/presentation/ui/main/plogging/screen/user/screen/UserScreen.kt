package com.example.planet.presentation.ui.main.plogging.screen.user.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.common.RedTextButton
import com.example.planet.presentation.util.numberComma
import com.example.planet.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    mainViewModel: MainViewModel,
    onClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        launch {
            mainViewModel.getUserInfo()
        }
        launch {
            mainViewModel.getMySeasonRanking()
        }
        launch {
            mainViewModel.getMyUniversityInfo()
        }
    }

    val scrollState = rememberScrollState()
    val redButtonTextColor = colorResource(id = R.color.red)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mainViewModel.userInfo.collectAsStateWithLifecycle().value?.let { userInfo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f),
                        shape = CircleShape
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(model = userInfo.imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = userInfo.nickName,
                            color = colorResource(id = R.color.font_background_color1),
                            fontSize = 16.sp
                        )
                        Text(
                            text = userInfo.message,
                            color = colorResource(id = R.color.font_background_color2),
                            fontSize = 10.sp
                        )
                    }
                }
            }
            RedTextButton(text = { "로그아웃" }, textColor = { redButtonTextColor }) {

            }
        }

        Spacer(modifier = Modifier.height(9.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.font_background_color3),
                contentColor = colorResource(id = R.color.font_background_color2)
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = { onClick() },
        ) {
            Text(text = "프로필 수정", fontSize = 10.sp)
        }

        MiddleHeader(
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 점수"
        )
        mainViewModel.myUniversityInfo.collectAsStateWithLifecycle().value?.let { myUniversityInfo ->
            MyProfileInfoLayout(
                image = myUniversityInfo.imageUrl,
                title = myUniversityInfo.name,
                subTitle1 = "기여 점수",
                subTitle3 = "통계",
                score = myUniversityInfo.score.numberComma(),
                subContentDesc1 = "",
                subContent3 = painterResource(id = R.drawable.statistics_image)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "학교 등수", fontSize = 10.sp)
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${myUniversityInfo.rank}등",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(9.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.font_background_color3),
                contentColor = colorResource(id = R.color.font_background_color2)
            ),
            shape = RoundedCornerShape(5.dp),
            onClick = { /*TODO*/ },
        ) {
            Text(text = "대학교 등록 및 변경", fontSize = 10.sp)
        }

        MiddleHeader(
            image = painterResource(id = R.drawable.plogging_user_seasonscorelogo),
            title = "시즌 점수"
        )
        mainViewModel.mySeasonRank.collectAsStateWithLifecycle().value?.let { mySeasonInfo ->
            MyProfileInfoLayout(
                image = mySeasonInfo.tierImageUrl,
                title = mySeasonInfo.tierName,
                subTitle1 = "점수",
                subTitle3 = "통계",
                score = mySeasonInfo.score.numberComma(),
                subContentDesc1 = "상위 1.2%",
                subContent3 = painterResource(id = R.drawable.statistics_image2)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "전 시즌", fontSize = 11.sp)
                    Image(
                        painter = painterResource(id = R.drawable.temporary_tier1),
                        contentDescription = null,
                        modifier = Modifier.height(25.dp)
                    )
                }
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        MiddleHeader(
            image = painterResource(id = R.drawable.communitylogo),
            title = "커뮤니티"
        )
        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "내가 작성한 게시물",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }
        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "내가 작성한 댓글",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }
        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "내가 신고한 게시물",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }
        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "내가 신고한 댓글",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        MiddleHeader(
            image = painterResource(id = R.drawable.adminlogo),
            title = "관리"
        )

        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "개발자 소개",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }
        Column(modifier = Modifier.clickable { }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 31.dp, top = 8.dp, bottom = 8.dp),
                text = "설정",
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
        }

    }
}

@Composable
fun MiddleHeader(image: Painter, title: String) {
    Spacer(modifier = Modifier.height(14.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = image,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(11.dp))
        Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun MyProfileInfoLayout(
    image: String,
    title: String,
    subTitle1: String,
    subTitle3: String,
    score: String,
    subContentDesc1: String,
    subContent3: Painter,
    subTitleContent2: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .weight(0.2f)
                .align(Alignment.Bottom),
        )

        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    thickness = 2.dp,
                    color = colorResource(id = R.color.font_background_color3),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
//                        .padding(horizontal = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = subTitle1, fontSize = 10.sp)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$score 점",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = subContentDesc1, /* TODO(승민이가 api 수정하면 바꿀 것)*/
                            fontSize = 8.sp,
                            color = colorResource(id = R.color.font_background_color2)
                        )
                    }
                }
                Divider(
                    thickness = 2.dp,
                    color = colorResource(id = R.color.font_background_color3),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
//                        .padding(horizontal = 16.dp)
                )
                subTitleContent2()

                Divider(
                    thickness = 2.dp,
                    color = colorResource(id = R.color.font_background_color3),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
//                        .padding(horizontal = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = subTitle3, fontSize = 11.sp)
                    Image(
                        painter = subContent3,
                        contentDescription = null,
                        modifier = Modifier.height(35.dp)
                    )
                }
            }
        }
    }
}