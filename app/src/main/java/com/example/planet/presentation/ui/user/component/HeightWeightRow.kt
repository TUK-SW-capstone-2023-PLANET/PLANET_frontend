package com.example.planet.presentation.ui.user.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.component.common.RedTextButton

@Composable
fun HeightWeightRow(
    heightText: () -> String,
    heightTextValue: (String) -> Unit,
    heightPlaceholder: String,
    weightText: () -> String,
    weightTextValue: (String) -> Unit,
    weightPlaceholder: String,
) {
    var enabled by remember { mutableStateOf(false) }
    val (redButtonText, redButtonColor) = if (!enabled) {
        Pair("변경", colorResource(id = R.color.red))
    } else {
        Pair("완료", colorResource(id = R.color.blue))
    }
    val focusRequesterList = listOf(remember { FocusRequester() }, remember { FocusRequester() })
    LaunchedEffect(enabled) {
        if (enabled) {
            focusRequesterList[0].requestFocus()
        }
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "키/몸무게",
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.2f),
            color = colorResource(id = R.color.font_background_color1),
        )
        UserHeightWeightTextField(
            modifier = Modifier
                .weight(0.3f)
                .focusRequester(focusRequesterList[0]),
            textValue = { heightText() },
            onValueChange = { heightTextValue(it) },
            placeholder = heightPlaceholder,
            suffix = "CM",
            enabled = { enabled },
            keyboardActions = { focusRequesterList[1].requestFocus() }
        )
        UserHeightWeightTextField(
            modifier = Modifier.weight(0.3f).focusRequester(focusRequesterList[1]),
            textValue = { weightText() },
            onValueChange = { weightTextValue(it) },
            placeholder = weightPlaceholder,
            suffix = "KG",
            enabled = { enabled },
            keyboardActions = {}
        )
        RedTextButton(
            text = { redButtonText },
            textColor = { redButtonColor },
            modifier = Modifier.weight(0.2f)
        ) {
            enabled = !enabled
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}