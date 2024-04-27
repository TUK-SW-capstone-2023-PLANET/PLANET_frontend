package com.example.planet.domain.repository

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface RankRepository {
    // Planet User 관련 ----------------------------------------------------------------------------
    // 플래넷 유저 랭킹 Top3 조회
    suspend fun getTop3PlanetUser(): Flow<ApiState>

    suspend fun getMyPlanetRanking(userId: Int = 0): Flow<ApiState>

    // 플래넷 유저 랭킹 전체 조회
    fun getAllPlanetUserRanking(): Flow<PagingData<PlanetRankingUser>>

    // Season 관련 ---------------------------------------------------------------------------------
    // 자대 대학교 나의 랭킹 정보 조회
    suspend fun getMySeasonRanking(userId: Int = 0): Flow<ApiState>

    // 시즌 유저 랭킹 5명 조회
    suspend fun getSeasonTop5UserInfo(userId: Int = 0): Flow<ApiState>

    fun getAllSeasonUser(): Flow<PagingData<SeasonUser>>

    // University 관련 -----------------------------------------------------------------------------
    /** 대학교 랭킹 3개 조회
     * @author fuck
     * @see getAllUniversity
     * @throws NullPointerException if non-exists username then throw exception
     * @param username 사용자이름 넣어라 병신아
     * @param University 사용자이름 넣어라 병신아 **/
//    @Throws(NullPointerException::class)
    suspend fun getHigherUniversities(): Flow<ApiState>

    /** 대학교 랭킹 전체 조회 **/
    fun getAllUniversity(): Flow<PagingData<University>>

    // UniversityUser 관련 -------------------------------------------------------------------------
    // 대학교 유저 랭킹 전체 조회
    fun getAllUniversityUser(): Flow<PagingData<UniversityUser>>

    // 나의 대학교 정보 조회
    fun getMyUniversityInfo(userId: Int): Flow<ApiState>


    // 대학교 유저 랭킹 4개 조회
    suspend fun getUniversityTop4User(userId: Int = 0): Flow<ApiState>

    // 자대 대학교 나의 랭킹 정보 조회
    suspend fun getUniversityMyRanking(userId: Int = 0): Flow<ApiState>
}