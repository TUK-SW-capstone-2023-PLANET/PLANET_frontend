package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
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
import com.example.planet.data.remote.dto.Advertisement
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.Tier
import com.example.planet.data.remote.dto.response.ranking.planet.HigherPlanetUser
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.ExpandedUniversityUser
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.domain.usecase.GetBannerUseCase
import com.example.planet.domain.usecase.GetTierListUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.ranking.planet.GetAllPlanetUserRankUseCase
import com.example.planet.domain.usecase.ranking.planet.GetHigherPlanetUserUseCase
import com.example.planet.domain.usecase.ranking.planet.GetMyPlanetRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetAllSeasonRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetHigherSeasonRankUseCase
import com.example.planet.domain.usecase.ranking.season.GetMySeasonRankUseCase
import com.example.planet.domain.usecase.ranking.university.GetAllUniversitiesUseCase
import com.example.planet.domain.usecase.ranking.university.GetHigherUniversitiesUseCase
import com.example.planet.domain.usecase.ranking.university.GetMyUniversityInfoUseCase
import com.example.planet.domain.usecase.ranking.university.GetMyUniversityRankUseCase
import com.example.planet.domain.usecase.ranking.universityuser.GetAllUniversityUserRankUseCase
import com.example.planet.domain.usecase.ranking.universityuser.GetHigherUniversityUserRankUseCase
import com.example.planet.domain.usecase.search.GetPlanetUserUseCase
import com.example.planet.domain.usecase.search.GetSeasonUseCase
import com.example.planet.domain.usecase.search.GetUniversityUseCase
import com.example.planet.domain.usecase.search.GetUniversityUserUseCase
import com.example.planet.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getAllUniversityUserRankUseCase: GetAllUniversityUserRankUseCase,
    private val getAllUniversitiesUseCase: GetAllUniversitiesUseCase,
    private val getHigherUniversitiesUseCase: GetHigherUniversitiesUseCase,
    private val getHigherUniversityUserRankUseCase: GetHigherUniversityUserRankUseCase,
    private val getMyUniversityRankUseCase: GetMyUniversityRankUseCase,
    private val getMySeasonRankUseCase: GetMySeasonRankUseCase,
    private val getMyPlanetRankUseCase: GetMyPlanetRankUseCase,
    private val getHigherSeasonRankUseCase: GetHigherSeasonRankUseCase,
    private val getAllSeasonRankUseCase: GetAllSeasonRankUseCase,
    private val getAllPlanetUserRankUseCase: GetAllPlanetUserRankUseCase,
    private val getHigherPlanetUserUseCase: GetHigherPlanetUserUseCase,
    private val getTierListUseCase: GetTierListUseCase,
    private val getMyUniversityInfoUseCase: GetMyUniversityInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getPlanetUserUseCase: GetPlanetUserUseCase,
    private val getSeasonUseCase: GetSeasonUseCase,
    private val getUniversityUseCase: GetUniversityUseCase,
    private val getUniversityUserUseCase: GetUniversityUserUseCase
    ) : ViewModel() {
    init {
        viewModelScope.launch {
            Log.d(TAG, "ViewModel init() 호출")
            async(Dispatchers.IO) { getUserToken() }.await()
            listOf(
                async(Dispatchers.IO) { getTop5SeasonUser() },
                async(Dispatchers.IO) { getTop4UniversityUser() },
                async(Dispatchers.IO) { getUniversityMyRanking(userId) },
                async(Dispatchers.IO) { getMySeasonRanking() },
                async(Dispatchers.IO) { getMyPlanetRanking() },
                async(Dispatchers.IO) { getMyUniversityInfo() },
                async(Dispatchers.IO) { getTopBanner() },
                async(Dispatchers.IO) { getTop3PlanetUser() },
                async(Dispatchers.IO) { getTop3Universities() }
            ).awaitAll()
        }
    }
    var themeState = derivedStateOf {
        switchState != 0
    }

    // userToken
    var userId: Long = 0

    // 자대 대학교 유저 TOP3 그래프 높이 list
    var universityUserGraphHeightList = emptyList<Dp>()

    // 대학교 TOP3 그래프 높이 list
    var universityGraphHeightList = listOf<Dp>(
        300.dp,
        300.dp,
        300.dp,
    )

    var switchState by mutableStateOf(0)


    private val _ploggingOrRecordSwitch = mutableStateOf(true)
    val ploggingOrRecordSwitch: State<Boolean> = _ploggingOrRecordSwitch

    // 상단배너 이미지 리스트
    var imageUrlList by mutableStateOf(listOf<String>())


    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    private val _myUniversityRank = MutableStateFlow<UniversityUser?>(null)
    val myUniversityRank: StateFlow<UniversityUser?> = _myUniversityRank.asStateFlow()

    private val _mySeasonRank = MutableStateFlow<SeasonUser?>(null)
    val mySeasonRank: StateFlow<SeasonUser?> = _mySeasonRank.asStateFlow()

    private val _myPlanetRank = mutableStateOf<PlanetRankingUser?>(null)
    val myPlanetRank: State<PlanetRankingUser?> = _myPlanetRank

    var higherMyUniversityUsers by mutableStateOf(emptyList<ExpandedUniversityUser>())

    var higherUniversity by mutableStateOf(emptyList<University>())

    var higherSeasonUsers by mutableStateOf(emptyList<SeasonUser>())

    private val _higherPlanetUsers = mutableStateListOf<HigherPlanetUser>()
    val higherPlanetUsers: List<HigherPlanetUser> = _higherPlanetUsers

    private val _totalUniversityUser =
        MutableStateFlow<PagingData<UniversityUser>>(PagingData.empty())
    val totalUniversityUser: MutableStateFlow<PagingData<UniversityUser>> = _totalUniversityUser

    private val _totalUniversity = MutableStateFlow<PagingData<University>>(PagingData.empty())
    val totalUniversity: MutableStateFlow<PagingData<University>> = _totalUniversity

    private val _totalSeasonUser = MutableStateFlow<PagingData<SeasonUser>>(PagingData.empty())
    val totalSeasonUser: MutableStateFlow<PagingData<SeasonUser>> = _totalSeasonUser

    private val _totalPlanetRankingUser =
        MutableStateFlow<PagingData<PlanetRankingUser>>(PagingData.empty())
    val totalPlanetRankingUser: MutableStateFlow<PagingData<PlanetRankingUser>> =
        _totalPlanetRankingUser

    private val _myUniversityInfo = MutableStateFlow<University?>(null)
    val myUniversityInfo: StateFlow<University?> = _myUniversityInfo.asStateFlow()


    private val _tierList = mutableStateOf(emptyList<Tier>())
    val tierList: State<List<Tier>> = _tierList

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    var planetRankResult by mutableStateOf<List<PlanetRankingUser>>(emptyList())
    var universityRankResult by mutableStateOf<List<University>>(emptyList())
    var seasonRankResult by mutableStateOf<List<SeasonUser>>(emptyList())
    var universityUserRankResult by mutableStateOf<List<UniversityUser>>(emptyList())


    val changeSearchText: (String) -> Unit = { text ->
        _searchText.value = text
    }

    // 자대 대학교 유저 기준, 1등 ~ 3등까지 score list를 반환
    private fun getGraphHeightList(list: List<ExpandedUniversityUser>): List<Dp> {
        val graphHeight1th = 120 // 기준을 120dp로 잡음
        val graphHeight2th = (graphHeight1th * list[2].score) / list[1].score
        val graphHeight3th = (graphHeight1th * list[3].score) / list[1].score
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

    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> { userId = result.value as Long }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getTopBanner() {
        // TODO(로딩 중 스켈리톤 ui 적용하기)
        when (val apiState = getBannerUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Advertisement>
                result.forEach { imageUrlList = imageUrlList.toList() + it.imageUrl }
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
                result.forEach { _higherPlanetUsers.add(it) }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTop3PlanetUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAllPlanetUserRanking() {
        getAllPlanetUserRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalPlanetRankingUser.value = it
        }
    }

    suspend fun getMySeasonRanking() {
        when (val apiState = getMySeasonRankUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _mySeasonRank.emit(apiState.value as SeasonUser)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMySeasonRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getMyPlanetRanking() {
        when (val apiState = getMyPlanetRankUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _myPlanetRank.value = apiState.value as PlanetRankingUser
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMyPlanetRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    suspend fun getTop5SeasonUser() {
        when (val apiState = getHigherSeasonRankUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, SeasonUser>>
                higherSeasonUsers = result[0].values.toList()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTop5SeasonUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAllSeasonUser() {
        getAllSeasonRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalSeasonUser.value = it
        }
    }


    suspend fun getTierList() {
        when (val apiState = getTierListUseCase().first()) {
            is ApiState.Success<*> -> {
                _tierList.value = apiState.value as List<Tier>
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTierList() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getTop3Universities() {
        when (val apiState = getHigherUniversitiesUseCase().first()) {
            is ApiState.Success<*> -> {
                higherUniversity = (apiState.value as List<University>)
                universityGraphHeightList = getGraphHeightList1(list = higherUniversity)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTopHigherUniversities() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAllUniversities() {
        getAllUniversitiesUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalUniversity.value = it
        }
    }

    // 자대 대학교 유저 랭킹 전체 조회
    suspend fun getAllUniversityUser() {
        getAllUniversityUserRankUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _totalUniversityUser.value = it
        }
    }

    suspend fun getTop4UniversityUser() {
        when (val apiState = getHigherUniversityUserRankUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, ExpandedUniversityUser>>
                higherMyUniversityUsers = result[0].values.toList()

                universityUserGraphHeightList = getGraphHeightList(list = higherMyUniversityUsers)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityUserTop4Ranking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }

    suspend fun getUniversityMyRanking(userId: Long) {
        when (val apiState = getMyUniversityRankUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _myUniversityRank.emit(apiState.value as UniversityUser)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityMyRankingUseCase() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getMyUniversityInfo() {
        when (val apiState = getMyUniversityInfoUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _myUniversityInfo.emit(apiState.value as University)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMyUniversityInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getUserInfo() {
        when (val apiState = getUserInfoUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                _userInfo.emit(apiState.value as UserInfo)
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUserInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun searchPlanetUser(query: String) {
        when (val apiState = getPlanetUserUseCase(query).first()) {
            is ApiState.Success<*> -> {
                planetRankResult = apiState.value as List<PlanetRankingUser>
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "searchPlanetUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun searchUniversity(query: String) {
        when (val apiState = getUniversityUseCase(query).first()) {
            is ApiState.Success<*> -> {
                universityRankResult = apiState.value as List<University>
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "searchUniversity() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun searchUniversityUser(query: String) {
        when (val apiState = getUniversityUserUseCase(query, userId).first()) {
            is ApiState.Success<*> -> {
                universityUserRankResult = apiState.value as List<UniversityUser>
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "searchUniversityUser() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun searchSeason(query: String) {
        when (val apiState = getSeasonUseCase(query).first()) {
            is ApiState.Success<*> -> {
                seasonRankResult = apiState.value as List<SeasonUser>
                Log.d("daeYoung", "searchSeason() 성공: ${apiState.value}")
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "searchUniversity() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }


}