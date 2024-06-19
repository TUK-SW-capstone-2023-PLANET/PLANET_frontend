package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.map.SearchPlace
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.domain.usecase.hotplace.GetAllHotPlaces
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.search.GetRecentlyMapSearchUseCase
import com.example.planet.domain.usecase.search.GetSearchPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getRecentlyMapSearchUseCase: GetRecentlyMapSearchUseCase,
    private val getSearchPlaceUseCase: GetSearchPlaceUseCase,

) : ViewModel() {
//    var recentSearchList by mutableStateOf(emptyList<SearchInfo>())

    var userId: Long = 0

    var recentlySearch:ApiState by mutableStateOf(ApiState.Loading)


    init {
        viewModelScope.launch {
            getUserToken()
            readRecentlySearch(userId)
        }

    }

    fun search(text: String) {

    }
    fun delete(text: String) {
//        recentSearchList.removeAll { it.text == text }
    }

    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> { userId = result.value as Long }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
    suspend fun searchPlace(text: String, onSuccess:(DoubleArray) -> Unit) {
        when (val apiState = getSearchPlaceUseCase(userId, text).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as SearchPlace
                if (result.addressCheck) {
                    onSuccess(doubleArrayOf(result.location.latitude, result.location.longitude))
                } else {
                    Toast.makeText(context, "잘못된 주소 입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "searchUniversity() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun readRecentlySearch(userId: Long) {
        recentlySearch = getRecentlyMapSearchUseCase(userId).first()
    }
}



