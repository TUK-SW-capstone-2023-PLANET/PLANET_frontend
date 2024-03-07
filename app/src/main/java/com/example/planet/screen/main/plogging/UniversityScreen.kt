package com.example.planet.screen.main.plogging

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.component.main.SubTitle
import com.example.planet.component.main.SubTitleDescription
import com.example.planet.component.main.plogging.UniversityGraph
import kotlinx.coroutines.delay
import kotlin.math.round

@Composable
@Preview(showBackground = true)
fun UniversityScreen() {
//    var color by remember {
//        Animatable(initialValue = colorResource(id = R.color.main_color1))
//    }
    var visible by remember { mutableStateOf(false) }
    var GraphHeight2th: Int = round(120 / 1120921.0 * 921218.0).toInt()
    var GraphHeight3th: Int = round(120 / 1120921.0 * 218213.0).toInt()

    LaunchedEffect(Unit) {
        delay(300)
        Log.d(
            TAG,
            "GraphHeight2th: 120.dp, GraphHeight2th: ${GraphHeight2th.dp}, GraphHeight3th: ${GraphHeight3th.dp}"
        )
        visible = true
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            SubTitle(title = "대학교 순위", modifier = Modifier.padding(end = 8.dp))
            SubTitleDescription("학교를 인증하여, 학교의 위상을 높히세요!!")
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                UniversityGraph(
                    visible = visible,
                    universityLogo = painterResource(id = R.drawable.university1),
                    score = "921,218",
                    graphHeight = GraphHeight2th.dp,
                    colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                    universityName = "연세대학교",
                    medal = painterResource(id = R.drawable.ranking_number2)
                )
                UniversityGraph(
                    visible = visible,
                    universityLogo = painterResource(id = R.drawable.university2),
                    score = "1,120,921",
                    graphHeight = 120.dp,
                    colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                    universityName = "한국공학대학교",
                    medal = painterResource(id = R.drawable.ranking_number1)
                )
                UniversityGraph(
                    visible = visible,
                    universityLogo = painterResource(id = R.drawable.university3),
                    score = "218,213",
                    graphHeight = GraphHeight3th.dp,
                    colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                    universityName = "고려대학교",
                    medal = painterResource(id = R.drawable.ranking_number3)
                )
            }

        }
    }
}