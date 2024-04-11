package com.example.planet.repository

import com.example.planet.data.ApiState
import com.example.planet.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RankingRepository @Inject constructor(private val apiService: ApiService) {

    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 Top3 조회
    suspend fun getTop3PlanetUser(): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getTop3PlanetUser()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // Season 관련 ---------------------------------------------------------------------------------
    // 시즌 유저 랭킹 5명 조회
    suspend fun getSeasonTop5UserInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getSeasonTop5UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAllSeasonUserInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getAllSeasonUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    suspend fun getHigherUniversities(): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getHigherUniversities()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 랭킹 전체 조회
    suspend fun getAllUniversity(): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getAllUniversityRanking()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // UniversityUser 관련 -------------------------------------------------------------------------
    // 대학교 유저 랭킹 전체 조회
    suspend fun getUniversityAllUserInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getUniversityAllUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 유저 랭킹 4개 조회
    suspend fun getUniversityTop4UserInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getUniversityTop4UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 자대 대학교 나의 랭킹 정보 조회
    suspend fun getUniversityMyRanking(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getUniversityMyRanking(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 대학교 유저 3개 조회
//    suspend fun getUniversityTop3UserInfo(userId: Int = 0): Flow<ApiState> = flow{
//        kotlin.runCatching {
//            apiService.getUniversityTop3UserInfo(userId = userId)
//        }.onSuccess {
//            emit(ApiState.Success(it))
//        }.onFailure { error ->
//            error.message?.let { emit(ApiState.Error(it)) }
//        }
//    }.flowOn(Dispatchers.IO)
}