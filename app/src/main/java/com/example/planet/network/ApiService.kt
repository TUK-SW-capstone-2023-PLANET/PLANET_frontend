package com.example.planet.network

import com.example.planet.data.dto.Advertisement
import com.example.planet.data.dto.ImageUrl
import com.example.planet.data.dto.PloggingComplete
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.PloggingInfo
import com.example.planet.data.dto.ranking.SeasonUser
import com.example.planet.data.dto.Tier
import com.example.planet.data.dto.TrashCan
import com.example.planet.data.dto.ranking.University
import com.example.planet.data.dto.ranking.ExpandedUniversityUser
import com.example.planet.data.dto.ranking.UniversityUser
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>

    @GET("/plogging/id")
    suspend fun getPloggingId(): Int

    @Multipart
    @POST("/image")
    suspend fun postImage(@Part file: MultipartBody.Part): ImageUrl

    @POST("/plogging-live")
    suspend fun postPloggingLive(@Body ploggingImage: PloggingImage): List<Map<String, Int>>

    @POST("/plogging")
    suspend fun postPlogging(@Body ploggingInfo: PloggingInfo): PloggingComplete

    @GET("/advertisement")
    suspend fun getTopBanner(): List<Advertisement>

    // University 관련 -----------------------------------------------------------------------------
    // 대학교 랭킹 3개 조회
    @GET("/university/rank")
    suspend fun getHigherUniversities(): List<University>

    // 대학교 랭킹 전체 조회
    @GET("/university/rank/all")
    suspend fun getAllUniversityRanking(): List<Map<Int, University>>

    // UniversityUser 관련 -------------------------------------------------------------------------
    // 자대 대학교 유저 랭킹 전체 조회
    @GET("/user/{userId}/rank/university/all")
    suspend fun getUniversityAllUserInfo(@Path("userId") userId: Int): List<Map<Int, ExpandedUniversityUser>>
    // 자대 대학교 유저 랭킹 상위 4명 조회
    @GET("/user/{userId}/rank/university/4")
    suspend fun getUniversityTop4UserInfo(@Path("userId") userId: Int): List<Map<Int, ExpandedUniversityUser>>

    // 자대 대학교 유저 랭킹 상위 3명 조회
    @GET("/user/{userId}/rank/university")
    suspend fun getUniversityTop3UserInfo(@Path("userId") userId: Int): List<UniversityUser>

    // Season 관련 ---------------------------------------------------------------------------------
    // 시즌 유저 랭킹 5명 조회
    @GET("/season/rank/5/user/{userId}")
    suspend fun getSeasonTop5UserInfo(@Path("userId") userId: Int): List<Map<Int, SeasonUser>>

    @GET("/season/rank/all/user/{userId}")
    suspend fun getAllSeasonUserInfo(@Path("userId") userId: Int): List<Map<Int, SeasonUser>>
    @GET("/tier")
    suspend fun getTierList(): List<Tier>

}