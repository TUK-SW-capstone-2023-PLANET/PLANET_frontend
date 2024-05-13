package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CommunityViewModel @Inject constructor(): ViewModel() {

    var postedDialogState by mutableStateOf(false)
    var boardDialogState by mutableStateOf(false)

    var postingImageList by mutableStateOf(emptyList<String>())
    var postingTitleInput by mutableStateOf("")
    var postingContentInput by mutableStateOf("")

}


data class PostingData(
    val imageList: List<String>,
    val title: String,
    val content: String
)