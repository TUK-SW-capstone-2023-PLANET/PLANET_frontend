package com.example.planet.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.dto.Advertisement
import com.example.planet.data.dto.SeasonPerson
import com.example.planet.data.dto.Tier
import com.example.planet.data.dto.ranking.University
import com.example.planet.data.dto.ranking.ExpandedUniversityUser
import com.example.planet.data.dto.ranking.UniversityUser
import com.example.planet.usecase.GetTierListUseCase
import com.example.planet.usecase.GetBannerUseCase
import com.example.planet.usecase.GetSeasonInfoUseCase
import com.example.planet.usecase.ranking.GetHigherUniversitiesUseCase
import com.example.planet.usecase.ranking.GetUniversityAllUserInfoUseCase
import com.example.planet.usecase.ranking.GetHigherUniversityUserRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getUniversityAllUserUseCase: GetUniversityAllUserInfoUseCase,
    private val getHigherUniversitiesUseCase: GetHigherUniversitiesUseCase,
    private val getHigherUniversityUserRankingUseCase: GetHigherUniversityUserRankingUseCase,
    private val getSeasonInfoUseCase: GetSeasonInfoUseCase,
    private val getTierListUseCase: GetTierListUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            getTopHigherUniversities()
            getTopBanner()
            getUniversityAllUserInfo()
            getUniversityUserTop4Ranking()
            getUniversityUserTop3Ranking()
            getSeasonInfo()
        }
    }
    // 자대 대학교 유저 TOP3 그래프 높이 list
    var universityUserGraphHeightList = emptyList<Dp>()
    // 대학교 TOP3 그래프 높이 list
    var universityGraphHeightList = emptyList<Dp>()

    private val _ploggingOrRecordSwitch = mutableStateOf(true)
    val ploggingOrRecordSwitch: State<Boolean> = _ploggingOrRecordSwitch

    private val _imageUrlList = mutableStateListOf<String>()
    val imageUrlList: List<String> = _imageUrlList

    private val _myUniversityUserList = mutableStateListOf<ExpandedUniversityUser>()
    val myUniversityUserList: List<ExpandedUniversityUser> = _myUniversityUserList

    private val _myUniversityTop4RankingUsers = mutableStateListOf<ExpandedUniversityUser>()
    val myUniversityTop4RankingUsers: List<ExpandedUniversityUser> = _myUniversityTop4RankingUsers

    private val _myUniversityTop3RankingUsers = mutableStateListOf<UniversityUser>()
    val myUniversityTop3RankingUsers: List<UniversityUser> = _myUniversityTop3RankingUsers

    private val _seasonPerson = mutableStateListOf<SeasonPerson>()
    val seasonPerson: List<SeasonPerson> = _seasonPerson

    private val _tierList = mutableStateOf(emptyList<Tier>())
    val tierList: State<List<Tier>> = _tierList

    private val _mainTopSwitchIsShow = mutableStateOf<Boolean>(true)
    val mainTopSwitchIsShow: State<Boolean> = _mainTopSwitchIsShow

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _higherUniversity = mutableStateListOf<University>()
    val higherUniversity: List<University> = _higherUniversity

    fun changePloggingScreen() {
        _ploggingOrRecordSwitch.value = false
    }

    fun changeRecordScreen() {
        _ploggingOrRecordSwitch.value = true
    }

    fun changeSeasonScreen() {
        _tierList.value = emptyList()
    }

    fun mainTopSwitchOnHide() {
        _mainTopSwitchIsShow.value = false
    }

    fun mainTopSwitchOnShow() {
        _mainTopSwitchIsShow.value = true
    }

    val changeSearchText: (String) -> Unit = { text ->
        _searchText.value = text
    }

    // 자대 대학교 유저 기준, 1등 ~ 3등까지 score list를 반환
    private fun getGraphHeightList(list: List<UniversityUser>): List<Dp> {
        val graphHeight1th = 120 // 기준을 120dp로 잡음
        val graphHeight2th = (graphHeight1th * list[1].score) / list[0].score
        val graphHeight3th = (graphHeight1th * list[2].score) / list[0].score
        // 2등, 1등, 3등 순서대로 저장
        return listOf(graphHeight2th.dp, graphHeight1th.dp, graphHeight3th.dp)
    }

    private fun getGraphHeightList1(list: List<University>): List<Dp> {
        val graphHeight1th = 120 // 기준을 120dp로 잡음
        val graphHeight2th = (graphHeight1th * list[1].score) / list[0].score
        val graphHeight3th = (graphHeight1th * list[2].score) / list[0].score
        // 2등, 1등, 3등 순서대로 저장
        return listOf(graphHeight2th.dp, graphHeight1th.dp, graphHeight3th.dp)
    }

    private suspend fun getTopBanner() {
        // TODO(로딩 중 스켈리톤 ui 적용하기)
        when (val apiState = getBannerUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Advertisement>
                result.forEach {
                    _imageUrlList.add(it.imageUrl)
                }
                Log.d(TAG, "getTopBanner() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTopBanner() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    private suspend fun getUniversityAllUserInfo() {
        when (val apiState = getUniversityAllUserUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, ExpandedUniversityUser>>
                result[0].values.forEach {
                    _myUniversityUserList.add(it)
                }
                Log.d(TAG, "getUniversityAllUserInfo() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityAllUserInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getSeasonInfo() {
        when (val apiState = getSeasonInfoUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, SeasonPerson>>
                result[0].values.forEach {
                    _seasonPerson.add(it)
                }
                Log.d(TAG, "getSeasonInfo() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getSeasonInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getTierList() {
        when (val apiState = getTierListUseCase().first()) {
            is ApiState.Success<*> -> {
                _tierList.value = apiState.value as List<Tier>
                Log.d(TAG, "getTierList() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTierList() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getTopHigherUniversities() {
        when (val apiState = getHigherUniversitiesUseCase().first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getTopHigherUniversities() 성공: ${apiState.value as List<University>}")
                (apiState.value as List<University>).forEach { university ->
                    _higherUniversity.add(university)
                }
                universityGraphHeightList = getGraphHeightList1(list = higherUniversity)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "getTopHigherUniversities() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getUniversityUserTop4Ranking() {
        when (val apiState = getHigherUniversityUserRankingUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, ExpandedUniversityUser>>
                result[0].values.forEach { university ->
                    _myUniversityTop4RankingUsers.add(university)
                }
                Log.d(TAG, "getUniversityUserTop4Ranking() 성공: $higherUniversity")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityUserTop4Ranking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getUniversityUserTop3Ranking() {
        when (val apiState = getHigherUniversityUserRankingUseCase.top3().first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getUniversityUserTop3Ranking() 성공: $higherUniversity")
                (apiState.value as List<UniversityUser>).forEach { university ->
                    _myUniversityTop3RankingUsers.add(university)
                }
                universityUserGraphHeightList = getGraphHeightList(list = myUniversityTop3RankingUsers)

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityUserTop3Ranking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
}