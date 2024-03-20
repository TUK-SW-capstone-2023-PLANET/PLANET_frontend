package com.example.planet.component.map.trashList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planet.R

@Composable
fun TrashCard(trash: String, count: Int, image: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = trash, modifier = Modifier.padding(bottom = 8.dp), color = colorResource(id = R.color.font_background_color1))
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "개수", color = colorResource(id = R.color.font_background_color1))
                    Text(text = count.toString(), color = colorResource(id = R.color.font_background_color1), fontWeight = FontWeight.Medium)
                }
                Divider(
//                        color = Color.Red,
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(1.dp)
                )
                Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "점수", color = colorResource(id = R.color.font_background_color1))
                    Text(text = "4", color = colorResource(id = R.color.font_background_color1), fontWeight = FontWeight.Medium)
                }
            }
        }
    }

}