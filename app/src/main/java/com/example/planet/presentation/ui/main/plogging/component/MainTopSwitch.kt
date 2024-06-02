package com.example.planet.presentation.ui.main.plogging.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.planet.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun MainTopSwitch(
    modifier: Modifier = Modifier,
    isChecked: Int,
    onCheckedChange: (checked: Int) -> Unit
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val marbleHeight by remember(size) {
        mutableStateOf(size.height.div(1.3f))
    }
    val marbleWidth by remember(size) {
        mutableStateOf(size.width.div(2.2f))
    }
    val yOffset by remember(size, marbleHeight) {
        mutableStateOf((size.height.div(2) - marbleHeight.div(2f)).toInt())
    }

    val marblePadding = 4.dp.value
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(isChecked)

//    val backgroundColor = animateColorAsState(
//        targetValue = if (swipeableState.currentValue != 0) Color(0xFF34C759) else Color(0xD6787880)
//    )
//    val sizePx = size.width.minus(marbleWidth + marblePadding.times(5))
    val sizePx = size.width.minus(marbleWidth + marblePadding.times(5))


    val anchors = mapOf(0f to 0, sizePx + 1f to 1)
    LaunchedEffect(key1 = swipeableState.currentValue, block = {
        onCheckedChange.invoke(swipeableState.currentValue)
    })


    val leftTextStyle = if (isChecked == 0) {
        TextStyle(
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    } else {
        TextStyle(
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

    val rightTextStyle = if (isChecked == 0) {
        TextStyle(
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    } else {
        TextStyle(
            fontSize = 11.sp,
            color =MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }


    Box(
        modifier = modifier
            .padding(16.dp)
            .aspectRatio(4.5f)
            .clip(RoundedCornerShape(10.dp))
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                enabled = false, //because you need to disable swipe
                orientation = Orientation.Horizontal
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (it.x > size.width.div(2))
                            scope.launch {
                                swipeableState.animateTo(
                                    1,
                                    anim = tween(250, easing = LinearEasing)
                                )
                            }
                        else
                            scope.launch {
                                swipeableState.animateTo(
                                    0,
                                    anim = tween(250, easing = LinearEasing)
                                )

                            }
                    }
                )
            }
            .background(MaterialTheme.colorScheme.surface)
            .onSizeChanged {
                size = it
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
//            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "플로깅",
                style = leftTextStyle,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "기록",
                style = rightTextStyle,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = marblePadding.dp)
                .offset {
                    IntOffset(
                        x = swipeableState.offset.value.roundToInt(),
                        y = yOffset
                    )
                }
//                .size(with(LocalDensity.current) { marbleSize.toDp() })
                .width(with(LocalDensity.current) { marbleWidth.toDp() })
                .height(with(LocalDensity.current) { marbleHeight.toDp() })
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        )
    }
}

@Composable
fun PloggingSwitchButton(modifier: Modifier = Modifier, text: String, enabled: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.48f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) Color.White else colorResource(id = R.color.font_background_color3),
            contentColor = if (enabled) colorResource(id = R.color.font_background_color1) else colorResource(
                id = R.color.font_background_color2
            )
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
    }
}

@Composable
fun RecordSwitchButton(modifier: Modifier = Modifier, text: String, enabled: Boolean) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.48f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!enabled) Color.White else colorResource(id = R.color.font_background_color3),
            contentColor = if (!enabled) colorResource(id = R.color.font_background_color1) else colorResource(
                id = R.color.font_background_color2
            )
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )
    }
}