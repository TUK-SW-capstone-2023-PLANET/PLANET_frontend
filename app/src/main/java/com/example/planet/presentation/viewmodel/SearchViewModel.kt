package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
//    var recentSearchList by mutableStateOf(emptyList<SearchInfo>())
    var recentSearchList by mutableStateOf(
        mutableListOf(
            SearchInfo(
                text = "안산",
                isAddress = false,
                date = "04.12"
            ),
            SearchInfo(
                text = "서울 마포구 와우산로23길 40",
                isAddress = true,
                date = "04.12"
            )
        )
    )

    fun search(text: String) {

    }
    fun delete(text: String) {
        recentSearchList.removeAll { it.text == text }
    }
}

data class SearchInfo(
    val text: String,
    val isAddress: Boolean,
    val date: String,
)