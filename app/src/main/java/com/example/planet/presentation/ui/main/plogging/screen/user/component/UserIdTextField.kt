package com.example.planet.presentation.ui.main.plogging.screen.user.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.component.common.RedTextButton
import com.example.planet.presentation.util.noRippleClickable
import kotlinx.coroutines.delay

@Composable
fun UserIdTextField(
    headerText: String,
    textValue: () -> String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = headerText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.2f),
            color = colorResource(id = R.color.font_background_color1),
        )
        OutlinedTextField(
            value = textValue(),
            onValueChange = { onValueChange(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.main_color2),
                focusedContainerColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.font_background_color3),
                disabledBorderColor = Color.Transparent,
            ),
            enabled = false,
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(0.6f)
                .height(50.dp),
            textStyle = TextStyle.Default.copy(fontSize = 10.sp),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(0.2f)
                .background(color = Color.Transparent)
        )
    }
    Row {
        Box(
            modifier = Modifier
                .weight(0.2f)
                .background(color = Color.Transparent)
        )
        Text(
            text = "*아이디는 특수문자 사용이 불가능합니다.",
            fontSize = 8.sp,
            color = colorResource(id = R.color.font_background_color2),
            modifier = Modifier.weight(0.8f)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun UserPwTextField(
    headerText: String,
    textValue: () -> String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {

    var showPassword by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }
    val (redButtonText, redButtonColor) = if (!enabled) {
        Pair("변경", colorResource(id = R.color.red))
    } else {
        Pair("완료", colorResource(id = R.color.blue))
    }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(enabled) {
        if (enabled) {
            focusRequester.requestFocus()
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = headerText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.2f),
            color = colorResource(id = R.color.font_background_color1),
        )
        OutlinedTextField(
            value = textValue(),
            onValueChange = { onValueChange(it) },
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.main_color2),
                focusedContainerColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.font_background_color3),
                disabledBorderColor = Color.Transparent,
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(0.6f)
                .height(50.dp)
                .focusRequester(focusRequester),
            textStyle = TextStyle.Default.copy(fontSize = 10.sp),
            trailingIcon = {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Visibility",
                    modifier = Modifier.noRippleClickable { showPassword = !showPassword }
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        RedTextButton(
            text = { redButtonText },
            textColor = { redButtonColor },
            modifier = Modifier.weight(0.2f)
        ) {
            enabled = !enabled
        }
    }
    Row {
        Box(
            modifier = Modifier
                .weight(0.2f)
                .background(color = Color.Transparent)
        )
        Text(
            text = "* 특수문자, 숫자, 대문자를 포함하여 8자 이상",
            fontSize = 8.sp,
            color = colorResource(id = R.color.font_background_color2),
            modifier = Modifier.weight(0.8f)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun UserHeightWeightTextField(
    modifier: Modifier = Modifier,
    textValue: () -> String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    suffix: String,
    enabled: () -> Boolean,
    keyboardActions: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = textValue(),
            onValueChange = { onValueChange(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.main_color2),
                focusedContainerColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.font_background_color3),
                disabledBorderColor = Color.Transparent,
            ),
            enabled = enabled(),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .weight(2f)
                .height(50.dp),
            textStyle = TextStyle.Default.copy(fontSize = 10.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { keyboardActions() }),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 11.sp,
                    color = colorResource(id = R.color.font_background_color2)
                )
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = suffix,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            color = colorResource(id = R.color.font_background_color2),
        )
    }
}

@Composable
fun UserNicknameTextField(
    text: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    trailingText: String,
    updateText: () -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }


    BasicTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        maxLines = 1,
//        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { updateText() }),
        decorationBox = { innerTextField ->
            innerTextField()
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = trailingText,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        style = LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            textAlign = TextAlign.End,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                )
            }
        }
    )
}