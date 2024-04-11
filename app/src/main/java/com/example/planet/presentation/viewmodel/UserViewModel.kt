package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    val maxNicknameTextLength = 20
    val maxDescribeTextLength = 20

    val idTextValue = mutableStateOf("")
    val pwTextValue = mutableStateOf("")
    var nicknameTextValue by mutableStateOf(TextFieldValue(text = "Happy Bean", selection = TextRange("Happy Bean".length)))
    var describeTextValue by mutableStateOf(TextFieldValue(text = "나랑 같이 플로깅 할 사람", selection = TextRange("나랑 같이 플로깅 할 사람".length)))
    var editNicknameState by mutableStateOf(false)
    var editDescribeState by mutableStateOf(false)

    val duplicateCheck by derivedStateOf {
        idTextValue.value.isNotEmpty()
    }

    val nicknameTextLength by derivedStateOf {
        "${nicknameTextValue.text.length} / $maxNicknameTextLength"
    }

    val describeTextLength by derivedStateOf {
        "${describeTextValue.text.length} / $maxDescribeTextLength"
    }

    fun changeEditNicknameScreen() {
        editNicknameState = !editNicknameState
    }

    fun changeEditDescribeScreen() {
        editDescribeState = !editDescribeState
    }

}