package com.example.planet.presentation.ui.main.plogging.user

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planet.R
import com.example.planet.component.common.RedTextButton
import com.example.planet.component.main.MainTopSwitch
import com.example.planet.presentation.viewmodel.MainViewModel

@Composable
fun UserScreen(navController: NavController, mainViewModel: MainViewModel, onClick: () -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTopSwitch(mainViewModel = mainViewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                            painter = painterResource(id = R.drawable.temporary_user_icon),
                            contentDescription = null,
                        )
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(userIconUrl)
//                            .crossfade(true).build(),
//                        contentDescription = null,
//                        modifier = Modifier
//                    )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = "HappyBean",
                            color = colorResource(id = R.color.font_background_color1),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "나랑 같이 플로깅 할 사람",
                            color = colorResource(id = R.color.font_background_color2),
                            fontSize = 10.sp
                        )
                    }
                }
                RedTextButton(text = "로그아웃")
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

            MyProfileInfoLayout(
                image = painterResource(id = R.drawable.university2),
                title = "한국공학대학교",
                subTitle1 = "기여 점수",
                subTitle3 = "통계",
                subContent1 = "1,120점",
                subContentDesc1 = "상위 0.01%",
                subContent3 = painterResource(id = R.drawable.statistics_image)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "기여 점수", fontSize = 10.sp)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "1,120점",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "상위 0.01%", /* TODO(승민이가 api 수정하면 바꿀 것)*/
                            fontSize = 8.sp,
                            color = colorResource(id = R.color.font_background_color2)
                        )
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

            MyProfileInfoLayout(
                image = painterResource(id = R.drawable.temporary_tier2),
                title = "DIAMOND",
                subTitle1 = "점수",
                subTitle3 = "통계",
                subContent1 = "2,180점",
                subContentDesc1 = "상위 1.2%",
                subContent3 = painterResource(id = R.drawable.statistics_image2)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 16.dp),
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
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            MiddleHeader(
                image = painterResource(id = R.drawable.communitylogo),
                title = "커뮤니티"
            )
            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "내가 작성한 게시물", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }
            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "내가 작성한 댓글", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }
            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "내가 신고한 게시물", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }
            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "내가 신고한 댓글", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            MiddleHeader(
                image = painterResource(id = R.drawable.adminlogo),
                title = "관리"
            )

            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "개발자 소개", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }
            Column(modifier = Modifier.clickable {  }) {
                Text(modifier = Modifier.fillMaxWidth().padding(start = 31.dp, top = 8.dp, bottom = 8.dp), text = "설정", fontSize = 12.sp, textAlign = TextAlign.Start)
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
            }

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
    image: Painter,
    title: String,
    subTitle1: String,
    subTitle3: String,
    subContent1: String,
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

//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(mainViewModel.higherSeasonUsers[0].tierImageUrl)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = null,
//                        modifier = Modifier.padding(bottom = 4.dp)
//                    )
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Column(
            modifier = Modifier
                .wrapContentWidth()
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
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    thickness = 2.dp,
                    color = colorResource(id = R.color.font_background_color3),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
                )
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = subTitle1, fontSize = 10.sp)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = subContent1,
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
                )
                subTitleContent2()

                Divider(
                    thickness = 2.dp,
                    color = colorResource(id = R.color.font_background_color3),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
                )
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 16.dp),
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