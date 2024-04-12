package com.example.planet.presentation.ui.main.plogging.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.main.SubTitle
import com.example.planet.component.main.SubTitleDescription
import com.example.planet.component.navigation.ScreenNav
import com.example.planet.presentation.ui.main.plogging.ranking.component.SeasonContentRow
import com.example.planet.presentation.ui.main.plogging.ranking.component.SeasonTitleRow
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import kotlinx.coroutines.launch

@Composable
fun SeasonScreen(navController: NavController, mainViewModel: MainViewModel) {
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
            Column(modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 16.dp)) {
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
                            navController.navigate(ScreenNav.TierScreen.screenRoute)
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
                        .data(mainViewModel.higherSeasonUsers[0].tierImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = mainViewModel.higherSeasonUsers[0].tierName, /* TODO(승민이가 api 수정하면 바꿀 것)*/
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
                    text = mainViewModel.higherSeasonUsers[0].userName,
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
                                text = "${mainViewModel.higherSeasonUsers[0].score.numberComma()}점",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "상위 0.01%", /* TODO(승민이가 api 수정하면 바꿀 것)*/
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
        mainViewModel.higherSeasonUsers.forEachIndexed { index, user ->
            SeasonContentRow(
                rank = user.rank,
                tier = user.tierImageUrl,
                nickname = user.userName,
                score = user.score.numberComma(),
                universityLogo = user.universityLogo
            )
        }
    }
}
