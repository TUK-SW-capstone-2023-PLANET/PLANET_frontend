package com.example.planet.presentation.ui.main.plogging.screen.community.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.core.component.dimension.Padding
import com.example.planet.R

@Composable
fun CommentDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    isFavorite: () -> Boolean,
    isMyComment: () -> Boolean,
    onSend: () -> Unit,
    onFavorite: () -> Unit,
    onDeleteFavorite: () -> Unit,
    onDeleteComment: () -> Unit,
    onReport: () -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
    )

    val deleteTextStyle = TextStyle(
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        color = colorResource(id = R.color.red)
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        modifier = modifier
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "쪽지 보내기",
                    style = textStyle,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            contentPadding = PaddingValues(0.dp),
            onClick = { /*TODO*/ })
        DropdownMenuItem(text = {
            Text(
                text = if (!isFavorite()) "공감하기" else "공감 취소",
                style = textStyle,
                modifier = Modifier.fillMaxWidth()
            )
        }, onClick = {
            if (!isFavorite()) onFavorite()
            else onDeleteFavorite()
        })
        DropdownMenuItem(text = {
            Text(
                text = "신고하기",
                style = textStyle,
                modifier = Modifier.fillMaxWidth()
            )
        }, onClick = { /*TODO*/ })
        if (isMyComment()) {
            DropdownMenuItem(text = {
                Text(
                    text = "삭제하기",
                    style = deleteTextStyle,
                    modifier = Modifier.fillMaxWidth()
                )
            }, onClick = { onDeleteComment() })
        }

    }
}