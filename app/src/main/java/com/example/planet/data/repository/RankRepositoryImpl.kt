package com.example.planet.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.data.repository.paging.PlanetPagingSource
import com.example.planet.data.repository.paging.SeasonUserPagingSource
import com.example.planet.data.repository.paging.UniversityPagingSource
import com.example.planet.data.repository.paging.UniversityUserPagingSource
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(private val mainApi: MainApi) :
    RankRepository {

    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 Top3 조회
    override suspend fun getTop3PlanetUser(): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getTop3PlanetUser()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMyPlanetRanking(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getMyPlanetRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)


    // 플래넷 유저 랭킹 전체 조회
    override fun getAllPlanetUserRanking(): Flow<PagingData<PlanetRankingUser>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PlanetPagingSource(mainApi) }).flow
    }

    // Season 관련 ---------------------------------------------------------------------------------
    // 자대 대학교 나의 랭킹 정보 조회
    override suspend fun getMySeasonRanking(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getMySeasonRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 시즌 유저 랭킹 5명 조회
    override suspend fun getSeasonTop5UserInfo(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getSeasonTop5UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllSeasonUser(): Flow<PagingData<SeasonUser>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { SeasonUserPagingSource(mainApi) }).flow
    }

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    override suspend fun getHigherUniversities(): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getHigherUniversities()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 랭킹 전체 조회
    override fun getAllUniversity(): Flow<PagingData<University>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { UniversityPagingSource(mainApi) }).flow
    }


    // UniversityUser 관련 -------------------------------------------------------------------------
    // 대학교 유저 랭킹 전체 조회
    override fun getAllUniversityUser(): Flow<PagingData<UniversityUser>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { UniversityUserPagingSource(mainApi) }).flow
    }

    // 나의 대학교 정보 조회
    override fun getMyUniversityInfo(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getMyUniversityInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)


    // 대학교 유저 랭킹 4개 조회
    override suspend fun getUniversityTop4User(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getUniversityTop4UserRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 자대 대학교 나의 랭킹 정보 조회
    override suspend fun getUniversityMyRanking(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getUniversityMyRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}