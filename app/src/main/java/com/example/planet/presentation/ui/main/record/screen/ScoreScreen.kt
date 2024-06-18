package com.example.planet.presentation.ui.main.record.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun ScoreScreen() {
    val titleStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    val trashTitleList = arrayOf(
        TrashDescription(
            R.drawable.paper,
            "종이",
            stringResource(id = R.string.paper)
        ),
        TrashDescription(
            R.drawable.papercup,
            "종이컵",
            stringResource(id = R.string.paper_cup)
        ),
        TrashDescription(
            R.drawable.can,
            "캔류",
            stringResource(id = R.string.can)
        ),
        TrashDescription(
            R.drawable.glass,
            "유리류",
            stringResource(id = R.string.glass)
        ),
        TrashDescription(
            R.drawable.pet,
            "패트류",
            stringResource(id = R.string.pet)
        ),
        TrashDescription(
            R.drawable.plastic,
            "플라스틱류",
            stringResource(id = R.string.plastic)
        ),
        TrashDescription(
            R.drawable.vinyl,
            "비닐류",
            stringResource(id = R.string.vinyl)
        ),
        TrashDescription(
            R.drawable.general_trash,
            "일반쓰레기",
            stringResource(id = R.string.general_trash)
        ),
        TrashDescription(
            R.drawable.styrofoam,
            "스티로폼",
            stringResource(id = R.string.styrofoam)
        ),
        TrashDescription(
            R.drawable.battery,
            "건전지",
            stringResource(id = R.string.battery)
        ),
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Row {
            Text(text = "플래넷 점수 시스템", style = titleStyle, modifier = Modifier.padding(end = 4.dp))
            Text(
                text = "PLANET SCORE SYSTEM",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 8.sp
            )
        }
        Text(
            text = "플래닛에서는 사용자가 주운 쓰레기의 종류를 분류해 줍는 난이도에 따라\n" +
                    "점수를 차등 지급하여 사용자의 플래닛 활동을 기록하고 추적할 수 있도록 합니다.\n" +
                    "또, 사용자의 러닝 기록을 바탕으로 점수를 지급하여 플로깅 활동을 촉진합니다.\n\n" +
                    "더 많은 점수를 획득하여 세상을 위한 나의 노력을 보여주세요!",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            fontSize = 10.sp,
            lineHeight = 13.sp
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )
        Text(text = "쓰레기 분류", style = titleStyle, modifier = Modifier.padding(bottom = 10.dp))
        trashTitleList.forEachIndexed { index, trashDescription ->
            TrashInfoCard(
                modifier = Modifier.padding(bottom = if (index != trashTitleList.lastIndex) 10.dp else 0.dp),
                image = trashDescription.image,
                name = trashDescription.name,
                description = trashDescription.description
            )

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )
        Text(text = "러닝 기록", style = titleStyle, modifier = Modifier.padding(bottom = 10.dp))
        TrashInfoCard(
            modifier = Modifier.padding(bottom = 36.dp),
            image = R.drawable.running,
            name = "하루 3KM",
            description = stringResource(id = R.string.running)
        )

    }
}

@Composable
fun TrashInfoCard(modifier: Modifier = Modifier, image: Int, name: String, description: String) {
    val titleStyle = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
    val contentStyle = TextStyle(
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 12.sp
    )


    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier
                .weight(4f)
                .padding(top = 3.dp)) {
                Text(text = name, style = titleStyle, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = description, style = contentStyle)
            }
        }
    }
}

data class TrashDescription(
    val image: Int,
    val name: String,
    val description: String
)