package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.component.Header

@Composable
fun CommunityScreen(
    startCommunityActivity: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            Header(
                title = "전체 게시판",
                description = "플래닛 사용자 모두와 소통해요!",
                modifier = Modifier.weight(0.2f)
            ) { startCommunityActivity() }
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)) {
                PopularPosting(
                    text = "플로깅 10년차의 플로깅 꿀팁!",
                    icon = Icons.Outlined.Person,
                    count = 1000,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(9.dp))
                PopularPosting(
                    text = "플로깅 10년차의 플로깅 꿀팁!",
                    icon = Icons.Outlined.Person,
                    count = 1000,
                    modifier = Modifier
                )
            }


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {

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
