package com.example.planet.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.planet.data.ApiState
import com.example.planet.data.dataSource.PlanetPagingSource
import com.example.planet.data.dataSource.UniversityPagingSource
import com.example.planet.data.dataSource.SeasonUserPagingSource
import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RankingRepository @Inject constructor(private val apiService: ApiService) {

    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 Top3 조회
    suspend fun getTop3PlanetUser(): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getTop3PlanetUser()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMyPlanetRanking(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getMyPlanetRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)


    // 플래넷 유저 랭킹 전체 조회
    fun getAllPlanetUserRanking(): Flow<PagingData<PlanetRankingUser>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PlanetPagingSource(apiService) }).flow
    }

    // Season 관련 ---------------------------------------------------------------------------------
    // 자대 대학교 나의 랭킹 정보 조회
    suspend fun getMySeasonRanking(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getMySeasonRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 시즌 유저 랭킹 5명 조회
    suspend fun getSeasonTop5UserInfo(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getSeasonTop5UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    fun getAllSeasonUser(): Flow<PagingData<SeasonUser>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { SeasonUserPagingSource(apiService) }).flow
    }

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    suspend fun getHigherUniversities(): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getHigherUniversities()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 랭킹 전체 조회
    fun getAllUniversity(): Flow<PagingData<University>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { UniversityPagingSource(apiService) }).flow
    }


    // UniversityUser 관련 -------------------------------------------------------------------------
    // 대학교 유저 랭킹 전체 조회
    suspend fun getAllUniversityUserInfo(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getAllUniversityUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 유저 랭킹 4개 조회
    suspend fun getUniversityTop4UserInfo(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getUniversityTop4UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 자대 대학교 나의 랭킹 정보 조회  TODO(페이징 적용할 것)
    suspend fun getUniversityMyRanking(userId: Int = 0): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getUniversityMyRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}