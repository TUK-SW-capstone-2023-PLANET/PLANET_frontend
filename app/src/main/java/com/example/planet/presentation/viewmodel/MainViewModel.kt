package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.remote.dto.Advertisement
import com.example.planet.data.remote.dto.Tier
import com.example.planet.data.remote.dto.response.ranking.planet.HigherPlanetUser
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.ExpandedUniversityUser
import com.example.planet.data.remote.dto.response.ranking.universityuser.MyRankingInfo
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.domain.usecase.GetBannerUseCase
import com.example.planet.domain.usecase.GetTierListUseCase
import com.example.planet.domain.usecase.ranking.universityuser.GetAllUniversityUserRankUseCase
import com.example.planet.domain.usecase.ranking.universityuser.GetHigherUniversityUserRankUseCase
import com.example.planet.domain.usecase.ranking.university.GetUniversitiesUseCase
import com.example.planet.domain.usecase.ranking.planet.GetAllPlanetUserRankUseCase
import com.example.planet.domain.usecase.ranking.planet.GetHigherPlanetUserUseCase
import com.example.planet.domain.usecase.ranking.planet.GetMyPlanetRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetAllSeasonRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetHigherSeasonRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetMySeasonRankUseCase
import com.example.planet.domain.usecase.ranking.university.GetMyUniversityRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getAllUniversityUserRankUseCase: GetAllUniversityUserRankUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getHigherUniversityUserRankUseCase: GetHigherUniversityUserRankUseCase,
    private val getMyUniversityRankingUseCase: GetMyUniversityRankingUseCase,
    private val getMySeasonRankUseCase: GetMySeasonRankUseCase,
    private val getMyPlanetRankUseCase: GetMyPlanetRankUseCase,
    private val getHigherSeasonRankUseCase: GetHigherSeasonRankUseCase,
    private val getAllSeasonRankUseCase: GetAllSeasonRankUseCase,
    private val getAllPlanetUserRankUseCase: GetAllPlanetUserRankUseCase,
    private val getHigherPlanetUserUseCase: GetHigherPlanetUserUseCase,
    private val getTierListUseCase: GetTierListUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {

            getTopBanner()

            getTop3PlanetUser()
            getTop5SeasonUser()
            getTop4UniversityUser()
            getTop3Universities()

            getMyUniversityRanking()
            getMySeasonRanking()
            getMyPlanetRanking()

            launch(Dispatchers.IO) {  getAllSeasonUser() }
            launch(Dispatchers.IO) {  getAllUniversities() }
            launch(Dispatchers.IO) {  getAllPlanetUserRanking() }
            launch(Dispatchers.IO) {  getAllUniversityUser() }

        }
    }
    // 자대 대학교 유저 TOP3 그래프 높이 list
    var universityUserGraphHeightList = emptyList<Dp>()
    // 대학교 TOP3 그래프 높이 list
    var universityGraphHeightList = emptyList<Dp>()

    private val _ploggingOrRecordSwitch = mutableStateOf(true)
    val ploggingOrRecordSwitch: State<Boolean> = _ploggingOrRecordSwitch

    // 상단배너 이미지 리스트
    var imageUrlList by mutableStateOf(listOf<String>())





    private val _myUniversityRankingInfo = mutableStateOf<MyRankingInfo?>(null)
    val myUniversityRankingInfo: State<MyRankingInfo?> = _myUniversityRankingInfo

    private val _mySeasonRank = mutableStateOf<SeasonUser?>(null)
    val mySeasonRank: State<SeasonUser?> = _mySeasonRank

    private val _myPlanetRank = mutableStateOf<PlanetRankingUser?>(null)
    val myPlanetRank: State<PlanetRankingUser?> = _myPlanetRank

    private val _higherMyUniversityUsers = mutableStateOf(emptyList<ExpandedUniversityUser>())
    val higherMyUniversityUsers: State<List<ExpandedUniversityUser>> = _higherMyUniversityUsers

    private val _higherUniversity = mutableStateListOf<University>()
    val higherUniversity: List<University> = _higherUniversity

    private val _higherSeasonUsers = mutableStateListOf<SeasonUser>()
    val higherSeasonUsers: List<SeasonUser> = _higherSeasonUsers

    private val _higherPlanetUsers = mutableStateListOf<HigherPlanetUser>()
    val higherPlanetUsers: List<HigherPlanetUser> = _higherPlanetUsers

    private val _totalUniversityUser = MutableStateFlow<PagingData<UniversityUser>>(PagingData.empty())
    val totalUniversityUser: MutableStateFlow<PagingData<UniversityUser>> = _totalUniversityUser

    private val _totalUniversity = MutableStateFlow<PagingData<University>>(PagingData.empty())
    val totalUniversity: MutableStateFlow<PagingData<University>> = _totalUniversity

    private val _totalSeasonUser = MutableStateFlow<PagingData<SeasonUser>>(PagingData.empty())
    val totalSeasonUser: MutableStateFlow<PagingData<SeasonUser>> = _totalSeasonUser

    private val _totalPlanetRankingUser = MutableStateFlow<PagingData<PlanetRankingUser>>(PagingData.empty())
    val totalPlanetRankingUser: MutableStateFlow<PagingData<PlanetRankingUser>> = _totalPlanetRankingUser



    private val _tierList = mutableStateOf(emptyList<Tier>())
    val tierList: State<List<Tier>> = _tierList

    private val _mainTopSwitchIsShow = mutableStateOf<Boolean>(true)
    val mainTopSwitchIsShow: State<Boolean> = _mainTopSwitchIsShow

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText



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
    private fun getGraphHeightList(list: List<ExpandedUniversityUser>): List<Dp> {
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
//                    _imageUrlList.add(it.imageUrl)
                    imageUrlList = imageUrlList.toList() + it.imageUrl
                }
                Log.d(TAG, "getTopBanner() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTopBanner() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    private suspend fun getTop3PlanetUser() {
        when (val apiState = getHigherPlanetUserUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<HigherPlanetUser>
                result.forEach {
                    _higherPlanetUsers.add(it)
                }
                Log.d(TAG, "getTop3PlanetUser() 성공: ${higherPlanetUsers}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTop3PlanetUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getAllPlanetUserRanking() {
        getAllPlanetUserRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalPlanetRankingUser.value = it
        }

    }
    private suspend fun getMySeasonRanking() {
        when (val apiState = getMySeasonRankUseCase().first()) {
            is ApiState.Success<*> -> {
                _mySeasonRank.value = apiState.value as SeasonUser
                Log.d(TAG, "getMySeasonRanking() 성공: ${mySeasonRank.value}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMySeasonRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getMyPlanetRanking() {
        when (val apiState = getMyPlanetRankUseCase().first()) {
            is ApiState.Success<*> -> {
                _myPlanetRank.value = apiState.value as PlanetRankingUser
                Log.d(TAG, "getMyPlanetRanking() 성공: ${myPlanetRank.value}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMyPlanetRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }



    private suspend fun getTop5SeasonUser() {
        when (val apiState = getHigherSeasonRankUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, SeasonUser>>
                result[0].values.forEach {
                    _higherSeasonUsers.add(it)
                }
                Log.d(TAG, "getTop5SeasonUser() 성공: ${higherSeasonUsers}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTop5SeasonUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getAllSeasonUser() {
        getAllSeasonRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalSeasonUser.value = it
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

    private suspend fun getTop3Universities() {
        when (val apiState = getUniversitiesUseCase.getHigherUniversity().first()) {
            is ApiState.Success<*> -> {
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

    private suspend fun getAllUniversities() {
        getUniversitiesUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalUniversity.value = it
        }
    }

    // 자대 대학교 유저 랭킹 전체 조회
    private suspend fun getAllUniversityUser() {
        getAllUniversityUserRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalUniversityUser.value = it
        }
    }
    private suspend fun getTop4UniversityUser() {
        when (val apiState = getHigherUniversityUserRankUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, ExpandedUniversityUser>>
                result[0].values.forEach { university ->
                    _higherMyUniversityUsers.value = higherMyUniversityUsers.value + university
                }
                universityUserGraphHeightList = getGraphHeightList(list = higherMyUniversityUsers.value)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityUserTop4Ranking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }

    private suspend fun getMyUniversityRanking() {
        when (val apiState = getMyUniversityRankingUseCase().first()) {
            is ApiState.Success<*> -> {
                _myUniversityRankingInfo.value = (apiState.value as MyRankingInfo)
                Log.d(TAG, "getUniversityMyRankingUseCase() 성공: $_myUniversityRankingInfo")

            }
            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityMyRankingUseCase() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
}