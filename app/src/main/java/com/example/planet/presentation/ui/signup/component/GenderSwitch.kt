package com.example.planet.presentation.ui.signup.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.util.noRippleClickable

@Composable
fun GenderSwitch(isMale: () -> Boolean, genderChange: (Boolean) -> Unit) {
    val (maleContainerColor, maleContentColor) = if (isMale()) {
        Pair(colorResource(id = R.color.main_color1), Color.White)
    } else {
        Pair(colorResource(id = R.color.font_background_color3), colorResource(id = R.color.font_background_color2))
    }
    val (femaleContainerColor, femaleContentColor) = if (!isMale()) {
        Pair(colorResource(id = R.color.main_color1), Color.White)
    } else {
        Pair(colorResource(id = R.color.font_background_color3), colorResource(id = R.color.font_background_color2))
    }

    Row {
        Card(
            modifier = Modifier.noRippleClickable { genderChange(true) },
            shape = RoundedCornerShape(3.dp),
            colors = CardDefaults.cardColors(
                containerColor = maleContainerColor,
                contentColor = maleContentColor
            )
        ) {
            Text(text = "남자", modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),)
        }
        Card(
            modifier = Modifier.noRippleClickable { genderChange(false) },
            shape = RoundedCornerShape(3.dp),
            colors = CardDefaults.cardColors(
                containerColor = femaleContainerColor,
                contentColor = femaleContentColor
            )
        ) {
            Text(text = "여자", modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),)
        }
    }
}