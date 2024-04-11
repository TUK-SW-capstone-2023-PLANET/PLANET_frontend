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
import com.example.planet.data.remote.dto.ranking.HigherPlanetUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.ranking.University
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.MySeasonRankingInfo
import com.example.planet.data.remote.dto.response.ranking.university.user.ExpandedUniversityUser
import com.example.planet.data.remote.dto.response.ranking.university.user.MyRankingInfo
import com.example.planet.data.remote.dto.response.ranking.university.user.UniversityUser
import com.example.planet.domain.usecase.GetBannerUseCase
import com.example.planet.domain.usecase.GetTierListUseCase
import com.example.planet.domain.usecase.ranking.GetHigherUniversityUserRankingUseCase
import com.example.planet.domain.usecase.ranking.GetPlanetUserUseCase
import com.example.planet.domain.usecase.ranking.GetSeasonUserUseCase
import com.example.planet.domain.usecase.ranking.GetUniversitiesUseCase
import com.example.planet.domain.usecase.ranking.GetAllUniversityUserInfoUseCase
import com.example.planet.domain.usecase.ranking.season.GetMySeasonRankingUseCase
import com.example.planet.domain.usecase.ranking.user.GetMyUniversityRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getAllUniversityUserUseCase: GetAllUniversityUserInfoUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase,
    private val getHigherUniversityUserRankingUseCase: GetHigherUniversityUserRankingUseCase,
    private val getMyUniversityRankingUseCase: GetMyUniversityRankingUseCase,
    private val getMySeasonRankingUseCase: GetMySeasonRankingUseCase,
    private val getSeasonUserUseCase: GetSeasonUserUseCase,
    private val getPlanetUserUseCase: GetPlanetUserUseCase,
    private val getTierListUseCase: GetTierListUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            getTopHigherUniversities()
            getTopBanner()
            getTop3PlanetUser()
            getAllPlanetUserRanking()
            getTop5SeasonUser()
            getAllSeasonUser()


            // trying to draw too large(105922560bytes) bitmap. 오류 해결 하고 주석 풀 것
            // getAllUniversities()
            getUniversityAllUserInfo()
            getUniversityUserTop4Ranking()
            getMyUniversityRanking()
            getMySeasonRanking()

            getAllUniversities()
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

    private val _myUniversityUserList = mutableStateListOf<UniversityUser>()
    val myUniversityUserList: List<UniversityUser> = _myUniversityUserList

    private val _myUniversityTop4RankingUsers = mutableStateListOf<ExpandedUniversityUser>()
    val myUniversityTop4RankingUsers: List<ExpandedUniversityUser> = _myUniversityTop4RankingUsers

    private val _myUniversityRankingInfo = mutableStateOf<MyRankingInfo?>(null)
    val myUniversityRankingInfo: State<MyRankingInfo?> = _myUniversityRankingInfo

    private val _mySeasonRankingInfo = mutableStateOf<MySeasonRankingInfo?>(null)
    val mySeasonRankingInfo: State<MySeasonRankingInfo?> = _mySeasonRankingInfo

    private val _higherUniversity = mutableStateListOf<University>()
    val higherUniversity: List<University> = _higherUniversity

    private val _totalUniversity = MutableStateFlow<PagingData<University>>(PagingData.empty())
    val totalUniversity: MutableStateFlow<PagingData<University>> = _totalUniversity

    private val _higherSeasonUsers = mutableStateListOf<SeasonUser>()
    val higherSeasonUsers: List<SeasonUser> = _higherSeasonUsers

    private val _totalSeasonUser = mutableStateListOf<SeasonUser>()
    val totalSeasonUser: List<SeasonUser> = _totalSeasonUser

    private val _higherPlanetUser = mutableStateListOf<HigherPlanetUser>()
    val higherPlanetUser: List<HigherPlanetUser> = _higherPlanetUser

    private val _planetRankingUser = mutableStateListOf<PlanetRankingUser>()
    val planetRankingUser: List<PlanetRankingUser> = _planetRankingUser

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
        when (val apiState = getPlanetUserUseCase.top3PlanetUser().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<HigherPlanetUser>
                result.forEach {
                    _higherPlanetUser.add(it)
                }
                Log.d(TAG, "getTop3PlanetUser() 성공: ${higherPlanetUser}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTop3PlanetUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getAllPlanetUserRanking() {
        when (val apiState = getPlanetUserUseCase().first()) {
            is ApiState.Success<*> -> {
                (apiState.value as List<PlanetRankingUser>).forEach {
                    _planetRankingUser.add(it)
                }
                Log.d(TAG, "getAllPlanetUserRanking() 성공: ${planetRankingUser}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAllPlanetUserRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
    private suspend fun getMySeasonRanking() {
        when (val apiState = getMySeasonRankingUseCase().first()) {
            is ApiState.Success<*> -> {
                _mySeasonRankingInfo.value = apiState.value as MySeasonRankingInfo
                Log.d(TAG, "getMySeasonRanking() 성공: ${mySeasonRankingInfo.value}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getMySeasonRanking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
    private suspend fun getTop5SeasonUser() {
        when (val apiState = getSeasonUserUseCase.getTop5SeasonUser().first()) {
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
        when (val apiState = getSeasonUserUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, SeasonUser>>
                result[0].values.forEach {
                    _totalSeasonUser.add(it)
                }
                Log.d(TAG, "getAllSeasonUser() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAllSeasonUser() 실패: ${apiState.errMsg}")
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
        when (val apiState = getUniversitiesUseCase.getHigherUniversity().first()) {
            is ApiState.Success<*> -> {
                (apiState.value as List<University>).forEach { university ->
                    _higherUniversity.add(university)
                }
                universityGraphHeightList = getGraphHeightList1(list = higherUniversity)

                Log.d(TAG, "getTopHigherUniversities() 성공: ${apiState.value as List<University>}")

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

        Log.d(TAG, "getAllUniversities(): ${totalUniversity}")

//        when (val apiState = getUniversitiesUseCase().first()) {

//            is ApiState.Success<*> -> {
//                (apiState.value as List<University>).forEach {
//                    _totalUniversity.add(it)
//                }
//                Log.d(TAG, "getAllUniversities() 성공: ${totalUniversity}")
//
//            }
//            is ApiState.Error -> {
//                Log.d("daeYoung", "getAllUniversities() 실패: ${apiState.errMsg}")
//            }
//
//            ApiState.Loading -> TODO()
//        }
    }

    // 자대 대학교 유저 랭킹 전체 조회
    private suspend fun getUniversityAllUserInfo() {
        when (val apiState = getAllUniversityUserUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, UniversityUser>>
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
    private suspend fun getUniversityUserTop4Ranking() {
        when (val apiState = getHigherUniversityUserRankingUseCase().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, ExpandedUniversityUser>>
                result[0].values.forEach { university ->
                    _myUniversityTop4RankingUsers.add(university)
                }
                Log.d(TAG, "getUniversityUserTop4Ranking() 성공: $myUniversityTop4RankingUsers")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityUserTop4Ranking() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
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