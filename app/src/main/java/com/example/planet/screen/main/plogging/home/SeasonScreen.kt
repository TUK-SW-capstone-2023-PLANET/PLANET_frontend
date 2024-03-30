package com.example.planet.screen.main.plogging.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.main.SubTitle
import com.example.planet.component.main.SubTitleDescription
import com.example.planet.component.main.plogging.SeasonContentRow
import com.example.planet.component.main.plogging.SeasonTitleRow
import com.example.planet.data.dto.SeasonPerson
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SeasonScreen(mainViewModel: MainViewModel) {
    var scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.wrapContentSize().padding(bottom = 16.dp)) {
                SubTitle(title = "대학교 순위", modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "PLANET TIER SYSTEM",
                    color = colorResource(id = R.color.font_background_color2),
                    fontSize = 9.sp
                )
            }
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable {
                        coroutineScope.launch {
                            mainViewModel.getTierList()
                        }
                    },
                tint = colorResource(id = R.color.font_background_color2)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
//            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = stringResource(id = R.string.mainscreen_ploggingpage_season_explanation),
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 11.sp,
                lineHeight = 18.sp,
            )
            Image(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                painter = painterResource(id = R.drawable.tier),
                contentDescription = null,
            )
        }

        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = colorResource(id = R.color.font_background_color3)
        )

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 16.dp)
        ) {
            SubTitle(title = "Spring Season IV", modifier = Modifier.padding(bottom = 4.dp))
            SubTitleDescription("아름다운 자연과 함께 봄의 주인공이 되어 보세요!")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
//                    .fillMaxWidth(0.3f)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mainViewModel.seasonPerson[0].tierImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Challenger",
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 10.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = mainViewModel.seasonPerson[0].userName,
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 15.sp,
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
                        Text(text = "점수", fontSize = 11.sp)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${mainViewModel.seasonPerson[0].score.numberComma()}점",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "상위 0.01%",
                                fontSize = 9.sp,
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
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "전 시즌", fontSize = 11.sp)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "정보 없음",
                                fontSize = 11.sp,
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
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "통계", fontSize = 11.sp)
                        Image(
                            painter = painterResource(id = R.drawable.statistics_image),
                            contentDescription = null,
                            modifier = Modifier.height(35.dp)
                        )
                    }
                }
            }

        }

        SeasonTitleRow()
        mainViewModel.seasonPerson.forEachIndexed { index, user ->
            when (index) {
                0 -> {}
                1 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color1),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                2 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                3 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
                4 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2).copy(alpha = 0.8f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
                5 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2).copy(alpha = 0.6f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
                6 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = Color(0xFFBED7EE).copy(alpha = 0.4f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
                else -> {
                    SeasonContentRow(
                        medal = {
//                            Spacer(modifier = Modifier.width(24.dp))
                            Spacer(modifier = Modifier.width(24.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
            }
        }
    }


}


@Composable
@Preview(showBackground = true)
fun Test() {
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                SubTitle(title = "대학교 순위", modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = "PLANET TIER SYSTEM",
                    color = colorResource(id = R.color.font_background_color2),
                    fontSize = 9.sp
                )
            }
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.font_background_color2)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
//            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = stringResource(id = R.string.mainscreen_ploggingpage_season_explanation),
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 11.sp,
                lineHeight = 18.sp,
            )
            Image(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                painter = painterResource(id = R.drawable.tier),
                contentDescription = null,
            )
        }

        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = colorResource(id = R.color.font_background_color3)
        )

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 16.dp)
        ) {
            SubTitle(title = "Spring Season IV", modifier = Modifier.padding(bottom = 4.dp))
            SubTitleDescription("아름다운 자연과 함께 봄의 주인공이 되어 보세요!")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
//                    .fillMaxWidth(0.3f)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Challenger",
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 10.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "행복한 정대영",
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 15.sp,
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
                        Text(text = "점수", fontSize = 11.sp)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${240.numberComma()}점",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "상위 0.01%",
                                fontSize = 9.sp,
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
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "전 시즌", fontSize = 11.sp)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "정보 없음",
                                fontSize = 11.sp,
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
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "통계", fontSize = 11.sp)
                        Image(
                            painter = painterResource(id = R.drawable.statistics_image),
                            contentDescription = null,
                            modifier = Modifier.height(35.dp)
                        )
                    }
                }
            }

        }

        val seasonPerson = mutableStateListOf<SeasonPerson>(
            SeasonPerson(
                userName = "행복한",
                rank = 2,
                universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
                score = 240,
                tierImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png"
            ),
            SeasonPerson(
                userName = "행복한",
                rank = 2,
                universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
                score = 240,
                tierImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png"
            ),
            SeasonPerson(
                userName = "행복한 정대영",
                rank = 2,
                universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
                score = 240,
                tierImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png"
            ),
            SeasonPerson(
                userName = "행복한 정대영",
                rank = 2,
                universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
                score = 240,
                tierImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png"
            ),
            SeasonPerson(
                userName = "행복한 정대영",
                rank = 2,
                universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
                score = 240,
                tierImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/tier/image+70.png"
            )
        )


        SeasonTitleRow()
        seasonPerson.forEachIndexed { index, user ->
            when (index) {
                0 -> {}
                1 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color1),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(24.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                2 -> {
                    SeasonContentRow(
                        medal = {
//                            Divider(
//                                color = colorResource(id = R.color.main_color2),
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(4.dp)
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
                            Spacer(modifier = Modifier.width(24.dp))

                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                3 -> {
                    SeasonContentRow(
                        medal = {
//                            Divider(
//                                color = colorResource(id = R.color.main_color2).copy(alpha = 0.8f),
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(4.dp)
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
                            Spacer(modifier = Modifier.width(24.dp))

                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                else -> {
                    SeasonContentRow(
                        medal = {
                            Spacer(modifier = Modifier.width(24.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
            }
        }
    }
}