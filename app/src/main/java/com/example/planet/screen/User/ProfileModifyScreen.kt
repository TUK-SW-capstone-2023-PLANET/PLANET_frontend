package com.example.planet.screen.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.util.noRippleClickable

@Composable
@Preview(showBackground = true)
fun ProfileModifyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = colorResource(id = R.color.font_background_color1),
            modifier = Modifier
                .align(Alignment.TopStart)
                .noRippleClickable {
                }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "프로필 수정",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
        Box(modifier = Modifier.wrapContentSize()) {
            Card(
                modifier = Modifier
                    .size(115.dp)
                    .aspectRatio(1f), shape = CircleShape
            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(userIconUrl)
//                        .crossfade(true).build(),
//                    contentDescription = null,
//                    modifier = Modifier
//                )
                Image(
                    painter = painterResource(id = R.drawable.temporary_user_icon),
                    contentDescription = null,
                )
            }
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.BottomEnd),
                elevation = CardDefaults.elevatedCardElevation(2.dp)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxSize(),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Happy Been", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.align(Alignment.CenterEnd))
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 8.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "나랑 같이 플로깅 할 사람", fontSize = 11.sp, modifier = Modifier.align(Alignment.Center))
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.align(Alignment.CenterEnd))
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 8.dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3)
        )
    }
}
